package com.example.notes.ui

import com.example.notes.modal.NoAuthException
import com.example.notes.modal.Repository

class SplashViewModel(private val repository: Repository = Repository) : BaseViewModel<Boolean?, SplashViewState>() {

    public fun requsesUser() {
        repository.getCurrentUser().observeForever { user ->
            viewStateLiveData.value = user?.let {
                SplashViewState(isAuth = true)
            } ?: SplashViewState(error = NoAuthException())

        }
    }

}