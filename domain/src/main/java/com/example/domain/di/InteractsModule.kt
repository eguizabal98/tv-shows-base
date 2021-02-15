package com.example.domain.di

import com.example.domain.interactors.account.*
import com.example.domain.interactors.detail.*
import com.example.domain.interactors.favorite.*
import com.example.domain.interactors.login.*
import com.example.domain.interactors.season.FetchSeasonsUseCase
import com.example.domain.interactors.season.FetchSeasonsUseCaseImpl
import com.example.domain.interactors.season.GetSeasonUseCase
import com.example.domain.interactors.season.GetSeasonUseCaseImpl
import com.example.domain.interactors.showlist.*
import org.koin.dsl.module

val interactsModule = module {
    //Authentication
    factory<GetAuthTokenUseCase> { GetAuthTokenUseCaseImpl(authRepository = get()) }
    factory<GetSessionIdUseCase> { GetSessionIdUseCaseImpl(authRepository = get()) }
    factory<GetAccountIdUseCase> { GetAccountIdUseCaseImpl(authRepository = get()) }
    factory<LogOutUseCase> { LogOutUseCaseImpl(authRepository = get()) }
    //ShowList
    factory<GetTvShowsUseCase<Any>> { GetTvShowsUseCaseImpl(showsRepository = get()) }
    factory<ChangeFilterUseCase> { ChangeFilterUseCaseImpl(showsRepository = get()) }
    factory<SetInitialFilter> { SetInitialFilterImpl(showsRepository = get()) }
    //Details
    factory<FetchDetailsUseCase> { FetchDetailsUseCaseImpl(detailsRepository = get()) }
    factory<FetchCastListUseCase> { FetchCastListUseCaseImpl(detailsRepository = get()) }
    factory<GetCreditsUseCase<Any>> { GetCreditsUseCaseImpl(detailsRepository = get()) }
    factory<GetDetailsUseCase<Any>> { GetDetailsUseCaseImpl(detailsRepository = get()) }
    //FavoriteShows
    factory<PutFavoriteUseCase> { PutFavoriteUseCaseImpl(favoritesRepository = get()) }
    factory<RefreshFavoritesUseCase> { RefreshFavoritesUseCaseImpl(favoritesRepository = get()) }
    factory<GetFavoritesUseCase<Any>> { GetFavoritesUseCaseImpl(favoritesRepository = get()) }
    //Seasons
    factory<FetchSeasonsUseCase> { FetchSeasonsUseCaseImpl(seasonsRepository = get()) }
    factory<GetSeasonUseCase<Any>> { GetSeasonUseCaseImpl(seasonsRepository = get()) }
    //Account
    factory<GetAccountUseCase> { GetAccountUseCaseImpl(accountRepository = get()) }
    factory<FetchAccountUseCase> { FetchAccountUseCaseImpl(accountRepository = get()) }

}