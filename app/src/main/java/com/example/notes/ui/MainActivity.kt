package com.example.notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.modal.Note
import com.example.notes.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var ui: ActivityMainBinding
        lateinit var viewModel: MainViewModel
        lateinit var adapter: MainAdapter

        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        setSupportActionBar(ui.toolbar)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = MainAdapter(object: OnItemClickListener {

            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })
        ui.mainRecycler.adapter = adapter

        viewModel.viewState().observe(this, Observer<MainViewState> { state ->
            state?.let { adapter.notes = state.notes }
        })

        ui.fab.setOnClickListener { openNoteScreen()}
    }

    private fun openNoteScreen(note: Note?= null){
        startActivity(NoteActivity.getStartIntent(this, note))
    }
}