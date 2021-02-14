package com.example.tvshowsbase.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.util.Connectivity
import com.example.tvshowsbase.details.ShowDetailViewModel
import com.example.tvshowsbase.login.LoginViewModel
import com.example.tvshowsbase.profile.ProfileViewModel
import com.example.tvshowsbase.seasons.SeasonsViewModel
import com.example.tvshowsbase.showslist.TvShowsViewModel
import com.example.data.util.ConnectivityImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module {
    viewModel {
        LoginViewModel(
            getAuthTokenUseCase = get(),
            getSessionIdUseCase = get(),
            getAccountIdUseCase = get()
        )
    }
    viewModel {
        TvShowsViewModel(
            getTvShowsUseCase = get(),
            refreshFavoritesUseCase = get(),
            logOutUseCase = get(),
            changeFilterUseCase = get(),
            setInitialFilter = get()
        )
    }
    viewModel {
        ShowDetailViewModel(
            fetchDetailsUseCase = get(),
            fetchCastListUseCase = get(),
            putFavoriteUseCase = get(),
            getFavoritesUseCase = get(),
            getCreditsUseCase = get(),
            getDetailsUseCase = get()
        )
    }
    viewModel { SeasonsViewModel(fetchSeasonsUseCase = get(), getSeasonUseCase = get()) }
    viewModel {
        ProfileViewModel(
            getAccountUseCase = get(),
            getFavoritesUseCase = get(),
            fetchAccountUseCase = get()
        )
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("sessionInfo", Context.MODE_PRIVATE)
    }

    factory<Connectivity> {
        ConnectivityImpl(context = androidContext())
    }

}