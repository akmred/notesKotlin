package com.example.notes.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.notes.modal.Note
import com.example.notes.modal.NoteResult
import com.example.notes.modal.Repository
import com.example.notes.ui.BaseViewModel
import com.example.notes.ui.NoteViewState

class NoteViewModel(private val repository: Repository = Repository): BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note){
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null){
            repository.saveNote(pendingNote!!)
        }
    }

    fun loadNote(noteId: String){
        repository.getNoteById(noteId).observeForever(object : Observer<NoteResult>{
            override fun onChanged(t: NoteResult?){
                if (t == null) return

                when (t) {
                    is NoteResult.Success<*> ->
                        viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                    is NoteResult.Error ->
                        viewStateLiveData.value = NoteViewState(error = t.error)
                }
            }
        })
    }

}