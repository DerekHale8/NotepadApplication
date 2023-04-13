package com.example.notepad2

import android.app.Application
import com.example.notepad2.db.NoteRoomDatabase
import com.example.notepad2.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NoteRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { NoteRepository(database.NoteDAO()) }
}
