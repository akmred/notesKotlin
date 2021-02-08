package com.example.notes.modal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

object Repository {

    private val notesLiveData = MutableLiveData<List<Note>>()

    val notes: MutableList<Note> = mutableListOf(
        Note(id = UUID.randomUUID().toString(),
             title = "My first note",
             note = "kotlin very shot"),

        Note(id = UUID.randomUUID().toString(),
                title = "My second note",
                note = "kotlin very shot",
                color = Color.BLUE),
        Note(id = UUID.randomUUID().toString(),
                title = "My third note",
                note = "kotlin very shot",
                color = Color.GREEN),
        Note(id = UUID.randomUUID().toString(),
                title = "My fifth note",
                note = "kotlin very shot",
                color = Color.PINK),
        Note(id = UUID.randomUUID().toString(),
                title = "My sixth note",
                note = "kotlin very shot",
                color = Color.RED),
        Note(id = UUID.randomUUID().toString(),
                title = "My seventh note",
                note = "kotlin very shot",
                color = Color.YELLOW)
        )

    init {
        notesLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>> = notesLiveData

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note){
        for (i in 0 until notes.size) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}