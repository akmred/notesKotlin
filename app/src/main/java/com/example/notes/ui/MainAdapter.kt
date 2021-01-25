package com.example.notes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.modal.Note

class MainAdapter: RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
      val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)

        return MainAdapter.NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
      holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val body = itemView.findViewById<TextView>(R.id.body)

        fun bind(note: Note){
            title.text = note.title
            // Заменил на with. title оставил, там разные размеры шрифта
            //body.text = note.note
            body.text  = with(note){
                "title: ${note.title}"+
                        "body: ${note.note}"
            }

            itemView.setBackgroundColor(note.Color)
        }
    }
}