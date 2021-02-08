package com.example.notes.ui

import com.example.notes.modal.Note

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error) {
}