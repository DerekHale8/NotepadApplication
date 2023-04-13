package com.example.notepad2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notepad2.db.Note
import com.example.notepad2.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(val repository: NoteRepository):ViewModel() {
    val allNotes:LiveData<List<Note>> = repository.allNotes.asLiveData()

    fun getNotesBySearch(searchText:String?):LiveData<List<Note>>{
        Log.d("DebugLog","LIVEDATA"+repository.getNotesBySearch(searchText).asLiveData().value?.size.toString())
        return repository.getNotesBySearch(searchText).asLiveData()

    }

    fun insert(note: Note) = viewModelScope.launch{
        repository.insertNote(note)
    }

    fun updateById(id:Int,title:String,text:String)=viewModelScope.launch {
        repository.updateById(id,title,text)
    }

    fun deleteById(id:Int)=viewModelScope.launch {
        repository.deleteById(id)
    }



}

class NoteViewModelFactory(val repository:NoteRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}