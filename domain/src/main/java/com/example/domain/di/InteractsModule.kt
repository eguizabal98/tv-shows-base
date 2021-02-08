package com.example.domain.di

import com.example.domain.interactors.login.*
import com.example.domain.interactors.tvshowsdetail.*
import com.example.domain.interactors.tvshowsfavorite.*
import com.example.domain.interactors.tvshowslist.GetTvShowsUseCase
import com.example.domain.interactors.tvshowslist.GetTvShowsUseCaseImpl
import org.koin.dsl.module

val interactsModule = module {
    factory<GetAuthTokenUseCase> { GetAuthTokenUseCaseImpl(authRepository = get()) }
    factory<GetSessionIdUseCase> { GetSessionIdUseCaseImpl(authRepository = get()) }
    factory<GetAccountIdUseCase> { GetAccountIdUseCaseImpl(authRepository = get()) }

    factory<GetTvShowsUseCase<Any>> { GetTvShowsUseCaseImpl(showsRepository = get()) }
    factory<GetShowDetailsLocalUseCase> { GetShowDetailsLocalUseCaseImpl(showDetailsRepository = get()) }
    factory<GetCastListUseCase> { GetCastListUseCaseImpl(showDetailsRepository = get()) }
    factory<PutShowFavoriteUseCase> { PutShowFavoriteUseCaseImpl(showsFavoritesRepository = get()) }
    factory<RefreshShowsFavoritesUseCase> {
        RefreshShowsFavoritesUseCaseImpl(
            showsFavoritesRepository = get()
        )
    }
    factory<GetFavoriteShowsUseCase> { GetFavoriteShowsUseCaseImpl(showsFavoritesRepository = get()) }
}