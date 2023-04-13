package com.example.notepad2.repository

import androidx.annotation.WorkerThread
import com.example.notepad2.db.Note
import com.example.notepad2.db.NoteDAO
import kotlinx.coroutines.flow.Flow

class NoteRepository(val noteDAO: NoteDAO) {
    val allNotes: Flow<List<Note>> = noteDAO.getNotes()

    fun getNotesBySearch(searchText:String?):Flow<List<Note>>{
        return noteDAO.getNotesBySearch(searchText)
    }


    @Suppress("RedudantSuspendFodifier")
    @WorkerThread
    suspend fun insertNote(note: Note){
        noteDAO.insert(note)
    }

    @Suppress("RedudantSuspendFodifier")
    @WorkerThread
    suspend fun deleteById(id:Int){
        noteDAO.deleteById(id)
    }

    @Suppress("RedudantSuspendFodifier")
    @WorkerThread
    suspend fun updateById(id:Int,title:String,text:String){
        noteDAO.updateById(id,title,text)
    }

}