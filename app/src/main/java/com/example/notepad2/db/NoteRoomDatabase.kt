package com.example.notepad2.db

import android.content.Context
import android.icu.text.CaseMap
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class],version=1, exportSchema = false)
abstract class NoteRoomDatabase:RoomDatabase() {

    abstract fun NoteDAO():NoteDAO

    companion object{
        @Volatile
        private var INSTANCE:NoteRoomDatabase?=null

        fun getDatabase(
            context: Context,
            scope:CoroutineScope
        ):NoteRoomDatabase{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java,
                    "notepad_database"
                )
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class NoteDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.NoteDAO())
                    }
                }
            }

            suspend fun populateDatabase(NoteDao: NoteDAO) {
                NoteDao.deleteAll()
                val word = Note(id=0,title = "Hello",text="It is first note from populate function")
                NoteDao.insert(word)
            }
        }
    }


}