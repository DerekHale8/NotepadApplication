package com.example.notepad2

import NoteListAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad2.db.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteEditActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    var isEdit=false
    var id:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        getIntents()
        saveNote()
    }



    fun saveNote(){
        val editTitleView:EditText = findViewById(R.id.edit_text_title)
        val editTextView:EditText = findViewById(R.id.edit_text_text)
        val buttonSave:FloatingActionButton=findViewById(R.id.fab_save)
        buttonSave.setOnClickListener {
                val title = editTitleView.text.toString()
                val text = editTextView.text.toString()

            Log.d("IntentViewId","ISEDIT:${isEdit}")
                if(isEdit){
                    Log.d("IntentViewId","UPDATE")
                    noteViewModel.updateById(id,title,text)
                }else{
                    Log.d("IntentViewId","SAVE")
                    var note=Note(id,title,text)
                    Log.d("IntentViewId",note.toString())
                    noteViewModel.insert(note)
                    Log.d("IntentViewId","INSERTED")
                }
            finish()

        }
    }

    fun getIntents(){
        val editTitleView:EditText = findViewById(R.id.edit_text_title)
        val editTextView:EditText = findViewById(R.id.edit_text_text)
        val intent= intent

        Log.d("IntentViewId","INTENT:${intent}")
        Log.d("IntentViewId","INTENTDATA:${intent.extras}")

        if(intent.extras!=null){
            Log.d("IntentViewId","INTENT:NOT NULL")
            id=intent.getIntExtra("id",0)
            editTitleView.setText(intent.getStringExtra("title"))
            editTextView.setText(intent.getStringExtra("text"))
            isEdit=true
        }
    }
}