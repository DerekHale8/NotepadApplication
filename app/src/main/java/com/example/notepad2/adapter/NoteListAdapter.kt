import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad2.NoteEditActivity
import com.example.notepad2.NoteViewModel
import com.example.notepad2.R
import com.example.notepad2.db.Note

class NoteListAdapter(private val context: Context) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(WordsComparator()) {
    //AsyncDifferConfig.Builder(WordsComparator()).build()) {
    //val context=contextM


    class NoteViewHolder(itemView: View,val contextViewHolder:Context) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle:TextView=itemView.findViewById(R.id.text_view_title)
        val textViewText:TextView=itemView.findViewById(R.id.text_view_text)
        val buttonDelete: Button =itemView.findViewById(R.id.button_delete)

        fun bind(title: String?,text: String?) {
            textViewTitle.text = title
            textViewText.text=text
        }



        companion object {
            fun create(parent: ViewGroup,contextViewHolder: Context): NoteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.note_item, parent, false)
                return NoteViewHolder(view,contextViewHolder)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent,context)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = currentList[position]
        holder.bind(current.title,current.text)
        holder.itemView.setOnClickListener {
            val intent= Intent(holder.contextViewHolder,NoteEditActivity::class.java).apply{
                putExtra("id",currentList[position].id)
                putExtra("title",currentList[position].title)
                putExtra("text",currentList[position].text)
            }
            holder.contextViewHolder.startActivity(intent)
        }
    }

    fun removeNote(position: Int){
        val currentList =  currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }






    class WordsComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem:Note): Boolean {
            return oldItem.title == newItem.title
        }
    }
}