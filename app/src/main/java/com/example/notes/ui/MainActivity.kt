package com.example.notes.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.modal.Note
import com.example.notes.viewmodel.MainViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogOutDialog.LogoutListener {

    override val viewModel: MainViewModel
        by lazy { ViewModelProvider(this).get(MainViewModel ::class.java)}

    override val ui: ActivityMainBinding
        by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(toolbar)

        adapter = MainAdapter(object : OnItemClickListener{
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })

        mainRecycler.adapter = adapter

        fab.setOnClickListener { openNoteScreen()}
    }

    private fun openNoteScreen(note: Note?= null){
        startActivity(NoteActivity.getStartIntent(this, note?.id.toString()))
    }

    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.logout -> showLogoutDialog().let{true}
            else -> false
        }

    private fun showLogoutDialog(){
        supportFragmentManager.findFragmentById(LogOutDialog.TAG) ?:
        LogOutDialog.createInstance().show(supportFragmentManager, LogOutDialog.TAG)
    }

    companion object{
        fun getStartIntent(content: Context) = Intent(content, MainActivity::class.java)
    }

    override fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener{
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }

    }
}