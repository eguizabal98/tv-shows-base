package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.util.Connectivity
import com.example.data.util.ConnectivityImpl
import com.example.domain.repository.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(networkAPI = get(), connectivity = get()) }
    factory<Connectivity> { ConnectivityImpl(context = androidContext()) }
}