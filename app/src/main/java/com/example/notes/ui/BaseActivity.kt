package com.example.notes.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.modal.Note
import com.example.notes.modal.RemoteDataProvider
import com.example.notes.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T, VS: BaseViewState<T>>: AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T,VS>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRes)

        viewModel.getViewState().observe(this, object : Observer<VS>{
            override fun onChanged(t: VS) {
                if (t == null) return
                if (t.data != null) renderData(t.data)
                if (t.error != null) renderError(t.error)
            }
        })

    }
    abstract fun renderData(data: T)

    protected fun renderError(error: Throwable){
        if (error.message != null) showError(error.message!!)
    }

    private fun showError(error: String){
        //TODO
    }

}