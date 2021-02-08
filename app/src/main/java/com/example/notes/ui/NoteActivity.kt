package com.example.notes.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.modal.Note
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.ActivityNoteBinding
import com.example.notes.modal.Color
import com.example.notes.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

const val SAVE_DELAY = 2000L

class NoteActivity:  AppCompatActivity(){

    companion object{
        private const val EXTRA_NOTE = "NoteActivity extra NOTE"

        fun getStartIntent(context: Context, note: Note?):Intent{
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            return intent
        }

    }
    private var note:Note? = null
    private lateinit var ui:ActivityNoteBinding
    private lateinit var viewModel: NoteViewModel

    private val textChangeListener = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable?) {
            triggerSaveNote()
        }
    }

    private fun createNewNote(): Note = Note(
            UUID.randomUUID().toString(),
            ui.titleEt.text.toString(),
            ui.bodyEt.text.toString()
    )

    private fun triggerSaveNote() {
        if (ui.titleEt.text == null || ui.titleEt.text!!.length  < 3 ) return

        Handler(Looper.getMainLooper()).postDelayed(object: Runnable {
            override fun run() {
                note = note?.copy(
                        title = ui.titleEt.text.toString(),
                        note = ui.bodyEt.text.toString(),
                        lastChanged = Date()
                ) ?: createNewNote()

                if (note != null) viewModel.saveChanges(note!!)
            }
        }, SAVE_DELAY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        ui = ActivityNoteBinding.inflate(layoutInflater)

        setContentView(ui.root)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if(note != null){
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }

        initView();

    }

    private fun initView() {
        if (note != null) {
            ui.titleEt.setText(note?.title ?: "")
            ui.bodyEt.setText(note?.note ?: "")
            val color = when (note?.color) {
                Color.WHITE -> R.color.color_white
                Color.VIOLET -> R.color.color_violet
                Color.YELLOW -> R.color.color_yello
                Color.RED -> R.color.color_red
                Color.PINK -> R.color.color_pink
                Color.GREEN -> R.color.color_green
                Color.BLUE -> R.color.color_blue

                else -> R.color.color_white
            }

            ui.toolbar.setBackgroundColor(resources.getColor(color))

        }

        ui.titleEt.addTextChangedListener(textChangeListener)
        ui.bodyEt.addTextChangedListener(textChangeListener)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        android.R.id.home -> {
            onBackPressed()
              true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
