package com.example.notepad2.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Query("SELECT * FROM notes_table")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE title LIKE '%' || :searchText || '%'")
    fun getNotesBySearch(searchText:String?): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("UPDATE notes_table SET title=:title,text=:text WHERE id=:id")
    suspend fun updateById(id:Int,title:String,text:String)

    @Query("DELETE FROM notes_table WHERE id=:id")
    suspend fun deleteById(id:Int)


    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()
}