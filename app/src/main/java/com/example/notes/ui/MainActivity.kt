package com.example.notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.modal.Note
import com.example.notes.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState() {

    override val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel ::class.java)}
    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(toolbar)

        adapter = MainAdapter(object : OnItemClickListener{
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })

        mainRecycler.adapter = adapter

        fab.setOnClickListener { openNoteScreen()}
    }

    private fun openNoteScreen(note: Note?= null){
        startActivity(NoteActivity.getStartIntent(this, note?.id))
    }

    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }
}