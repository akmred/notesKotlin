package com.example.notes

import com.example.notes.modal.FireStoreProvider
import com.example.notes.modal.RemoteDataProvider
import com.example.notes.modal.Repository
import com.example.notes.ui.SplashViewModel
import com.example.notes.viewmodel.MainViewModel
import com.example.notes.viewmodel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }

    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { NoteViewModel(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}