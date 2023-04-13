package com.example.notepad2

import NoteListAdapter
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad2.db.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    val adapter = NoteListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter=adapter
        Log.d("DebugLog","LIST"+adapter.currentList.size)

        getSwapMsg(adapter).attachToRecyclerView(recyclerView)

        val fabNewNote = findViewById<FloatingActionButton>(R.id.fabNewNote)
        fabNewNote.setOnClickListener {
            val intent = Intent(this, NoteEditActivity::class.java)
            startActivity(intent)
        }


        noteViewModel.allNotes.observe(this) { notes ->
            notes.let { adapter.submitList(it) }
        }

        val mainActivity=this


        val searchView:SearchView=findViewById(R.id.search_view_title)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                noteViewModel.getNotesBySearch(newText).observe(mainActivity) { notes ->
                    notes.let { adapter.submitList(it) }
                    Log.d("DebugLog","SIZE"+notes.size.toString())
                }

                return true
            }
        })
    }



    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            //val adapter=NoteListAdapter(this)
            //val intent = result.data
            val data: Intent? = result.data
            val isEdit=data?.getStringExtra("isEdit")
            val id=data?.getIntExtra("id",0)
            val title=data?.getStringExtra("title")
            val text=data?.getStringExtra("text")
            Log.d("IntentViewId",id.toString())
            Log.d("IntentViewId",isEdit.toString())
            if(isEdit=="true"){
                noteViewModel.updateById(id!!,title!!,text!!)
            }else{
                val word= Note(id = 0,title.toString(),text.toString())
                noteViewModel.insert(word)
            }
        }
        Log.d("IntentViewId","ACTIVITY RESULT")
    }

    fun getSwapMsg(adapter:NoteListAdapter):ItemTouchHelper{
        return ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                adapter.removeNote(position)
                noteViewModel.deleteById(adapter.currentList[position].id)

            }

        })
    }
}

