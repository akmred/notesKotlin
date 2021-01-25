package com.example.notes.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notes.modal.Note
import com.example.notes.modal.Repository

class NoteViewModel(private val repository: Repository = Repository): ViewModel() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note){
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null){
            repository.saveNote(pendingNote!!)
        }
    }


}