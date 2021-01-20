package com.example.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.modal.Repository
import com.example.notes.ui.MainViewState

class MainViewModel:ViewModel() {

   private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

   init {
       viewStateLiveData.value = MainViewState(Repository.getNotes())
   }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}