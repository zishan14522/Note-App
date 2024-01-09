package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(var notes: List<Notes>,private val notesInterface: NotesInterface, context: Context): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private var filteredItems: List<Notes> = notes



    private val db:NotesDatabaseHelper= NotesDatabaseHelper(context)
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView=itemView.findViewById(R.id.titleTextView)
        val checkBox: CheckBox=itemView.findViewById(R.id.checkBox)
        val contentTextView: TextView=itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView=itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView=itemView.findViewById(R.id.deleteButton)

    }



    //Filter For  SearchView
    fun filter(query: String) {
        filteredItems = notes.filter {
            it.title.contains(query, ignoreCase = true)
        }
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_items, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int =filteredItems.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note =filteredItems[position]
        holder.titleTextView.text=note.title
        holder.contentTextView.text=note.content
        holder.titleTextView.setTextColor(note.textColor)



        holder.itemView.setOnClickListener {
            notesInterface.getDetails(note.title,note.content)
        }


        holder.updateButton.setOnClickListener {
            val intent= Intent(holder.itemView.context,UpdateNoteActivity::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteNotes(note.id)
            refreshData(db.getAllData())
            Toast.makeText(holder.itemView.context,"Note Deleted", Toast.LENGTH_SHORT).show()
        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked==true){
                holder.itemView.setOnClickListener {
                }
                Toast.makeText(holder.itemView.context,"Add To Favorite", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(holder.itemView.context,"Remove To Favorite", Toast.LENGTH_SHORT).show()
            }

        }


    }
    fun refreshData(newNotes: List<Notes>){
        filteredItems=newNotes
        notifyDataSetChanged()
    }


}