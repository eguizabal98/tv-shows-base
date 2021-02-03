package com.example.tvshowsbase.di

import android.content.Context
import android.content.SharedPreferences
import com.example.tvshowsbase.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presenterModule = module {
    viewModel { LoginViewModel(getAuthTokenUseCase = get(), getSessionIdUseCase = get()) }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("sessionInfo", Context.MODE_PRIVATE)
    }

}