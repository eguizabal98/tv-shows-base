package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ShowDetailsRepositoryImpl
import com.example.data.repository.ShowsFavoritesRepositoryImpl
import com.example.data.repository.ShowsRepositoryImpl
import com.example.data.util.Connectivity
import com.example.data.util.ConnectivityImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ShowDetailsRepository
import com.example.domain.repository.ShowsFavoritesRepository
import com.example.domain.repository.ShowsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(authAPI = get(), connectivity = get()) }
    factory<ShowsRepository<Any>> { ShowsRepositoryImpl(dataBase = get()) }
    factory<ShowDetailsRepository> {
        ShowDetailsRepositoryImpl(
            tvShowDetailsAPI = get()
        )
    }
    factory<Connectivity> { ConnectivityImpl(context = androidContext()) }
    factory<ShowsFavoritesRepository> {
        ShowsFavoritesRepositoryImpl(
            favoriteShowAPI = get(),
            favoriteShowDao = get()
        )
    }
}