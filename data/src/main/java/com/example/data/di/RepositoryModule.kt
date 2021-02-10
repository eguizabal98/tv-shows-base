package com.example.data.di

import com.example.data.repository.*
import com.example.data.util.Connectivity
import com.example.data.util.ConnectivityImpl
import com.example.domain.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> {
        AuthRepositoryImpl(
            authAPI = get(),
            connectivity = get(),
            accountDao = get(),
            favoriteShowDao = get(),
            tvShowDao = get()
        )
    }
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
    factory<SeasonsRepository> { SeasonsRepositoryImpl(tvShowSeasonsAPI = get()) }
    factory<AccountRepository> { AccountRepositoryImpl(accountDao = get()) }
}