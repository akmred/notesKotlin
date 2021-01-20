package com.example.notes.modal

object Repository {

    private val notes: List<Note>

    init {

        notes = listOf(
            Note("My first note", "kotlin very shot", 0xfff06269.toInt()),
            Note("My second note", "kotlin very shot", 0xfff06269.toInt()),
            Note("My third note", "kotlin very shot", 0xfff06269.toInt()),
            Note("My fifth note", "kotlin very shot", 0xfff06269.toInt()),
            Note("My sixth note", "kotlin very shot", 0xfff06269.toInt()),
            Note("My seventh note", "kotlin very shot", 0xfff06269.toInt())
            )
    }

    fun getNotes():List<Note> = notes

}