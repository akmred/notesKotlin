package com.example.notes.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.modal.Note
import com.example.notes.modal.RemoteDataProvider
import com.example.notes.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T, VS : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, VS>
    abstract val layoutRes: Int
    abstract val ui: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ui.root)

        viewModel.getViewState().observe(this) { t ->
            t?.apply {
                data?.let { renderData(it) }
                error?.let { renderError(it) }
            }

        }

    }

    abstract fun renderData(data: T)

    protected open fun renderError(error: Throwable) {
        error.message?.let { showError(it) }
    }

    protected fun showError(error: String) {
        Snackbar.make(ui.root, error, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.snackbar_action) { dismiss() }
            show()
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
    }

}