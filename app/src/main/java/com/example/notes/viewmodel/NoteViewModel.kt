package com.example.notes.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.notes.modal.Note
import com.example.notes.modal.NoteResult
import com.example.notes.modal.Repository
import com.example.notes.ui.BaseViewModel
import com.example.notes.ui.NoteViewState

class NoteViewModel(private val repository: Repository) :
    BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val currentNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun saveChanges(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    override fun onCleared() {
        currentNote?.let { repository.saveNote(it) }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever({ t ->

            t?.let { noteResult ->
                viewStateLiveData.value = when (noteResult) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(note = noteResult.data as? Note))
                    is NoteResult.Error -> NoteViewState(error = noteResult.error)
                }
            }
        })
    }

    fun deleteNote() {
        currentNote?.let {
            repository.deleteNote(it.id).observeForever { result ->
                result?.let { noteResult ->
                    viewStateLiveData.value = when (noteResult) {
                        is NoteResult.Success<*> ->
                            NoteViewState(NoteViewState.Data(isDeleted = true))
                        is NoteResult.Error ->
                            NoteViewState(error = noteResult.error)
                    }
                }
            }
        }
    }


}