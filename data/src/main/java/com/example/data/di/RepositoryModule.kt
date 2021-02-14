package com.example.data.di

import com.example.data.repository.*
import com.example.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    factory<AuthRepository> {
        AuthRepositoryImpl(
            authAPI = get(),
            accountDao = get(),
            favoriteShowDao = get(),
            tvShowDao = get()
        )
    }
    factory<ShowsRepository<Any>> {
        ShowsRepositoryImpl(dataBase = get())
    }
    factory<DetailsRepository<Any, Any>> {
        DetailsRepositoryImpl(
            detailsAPI = get(),
            detailDao = get(),
            creditsDao = get()
        )
    }
    factory<FavoritesRepository<Any>> {
        FavoritesRepositoryImpl(
            favoritesAPI = get(),
            favoriteShowDao = get()
        )
    }
    factory<SeasonsRepository<Any>> {
        SeasonsRepositoryImpl(
            seasonsAPI = get(),
            seasonDao = get()
        )
    }
    factory<AccountRepository> {
        AccountRepositoryImpl(accountDao = get(), authAPI = get())
    }
}