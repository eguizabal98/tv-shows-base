package com.example.tvshowsbase.di

import android.content.Context
import android.content.SharedPreferences
import com.example.tvshowsbase.details.ShowDetailViewModel
import com.example.tvshowsbase.login.LoginViewModel
import com.example.tvshowsbase.seasons.SeasonsViewModel
import com.example.tvshowsbase.showslist.TvShowsViewModel
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
    viewModel { TvShowsViewModel(getTvShowsUseCase = get(), refreshShowsFavoritesUseCase = get()) }
    viewModel {
        ShowDetailViewModel(
            getShowDetailsLocalUseCase = get(),
            getCastListUseCase = get(),
            putShowFavoriteUseCase = get(),
            getFavoriteShowsUseCase = get()
        )
    }
    viewModel { SeasonsViewModel(getSeasonsUseCase = get()) }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("sessionInfo", Context.MODE_PRIVATE)
    }

}