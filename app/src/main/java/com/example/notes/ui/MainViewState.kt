package com.example.notes.ui

import com.example.notes.modal.Note

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
        BaseViewState<List<Note>?>(notes, error)



