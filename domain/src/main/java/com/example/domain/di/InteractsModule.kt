package com.example.domain.di

import com.example.domain.interactors.account.FetchAccountUseCase
import com.example.domain.interactors.account.FetchAccountUseCaseImpl
import com.example.domain.interactors.account.GetAccountUseCase
import com.example.domain.interactors.account.GetAccountUseCaseImpl
import com.example.domain.interactors.account.LogOutUseCase
import com.example.domain.interactors.account.LogOutUseCaseImpl
import com.example.domain.interactors.detail.FetchCastListUseCase
import com.example.domain.interactors.detail.FetchCastListUseCaseImpl
import com.example.domain.interactors.detail.FetchDetailsUseCase
import com.example.domain.interactors.detail.FetchDetailsUseCaseImpl
import com.example.domain.interactors.detail.GetCreditsUseCase
import com.example.domain.interactors.detail.GetCreditsUseCaseImpl
import com.example.domain.interactors.detail.GetDetailsUseCase
import com.example.domain.interactors.detail.GetDetailsUseCaseImpl
import com.example.domain.interactors.favorite.GetFavoritesUseCase
import com.example.domain.interactors.favorite.GetFavoritesUseCaseImpl
import com.example.domain.interactors.favorite.PutFavoriteUseCase
import com.example.domain.interactors.favorite.PutFavoriteUseCaseImpl
import com.example.domain.interactors.favorite.RefreshFavoritesUseCase
import com.example.domain.interactors.favorite.RefreshFavoritesUseCaseImpl
import com.example.domain.interactors.login.GetAccountIdUseCase
import com.example.domain.interactors.login.GetAccountIdUseCaseImpl
import com.example.domain.interactors.login.GetAuthTokenUseCase
import com.example.domain.interactors.login.GetAuthTokenUseCaseImpl
import com.example.domain.interactors.login.GetSessionIdUseCase
import com.example.domain.interactors.login.GetSessionIdUseCaseImpl
import com.example.domain.interactors.season.FetchSeasonsUseCase
import com.example.domain.interactors.season.FetchSeasonsUseCaseImpl
import com.example.domain.interactors.season.GetSeasonUseCase
import com.example.domain.interactors.season.GetSeasonUseCaseImpl
import com.example.domain.interactors.showlist.ChangeFilterUseCase
import com.example.domain.interactors.showlist.ChangeFilterUseCaseImpl
import com.example.domain.interactors.showlist.GetTvShowsUseCase
import com.example.domain.interactors.showlist.GetTvShowsUseCaseImpl
import com.example.domain.interactors.showlist.SetInitialFilter
import com.example.domain.interactors.showlist.SetInitialFilterImpl
import com.example.domain.repository.AccountRepository
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DetailsRepository
import com.example.domain.repository.FavoritesRepository
import com.example.domain.repository.SeasonsRepository
import com.example.domain.repository.ShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object InteractsModule {
    // Authentication
    @Provides
    fun provideGetAuthTokenUseCase(authRepository: AuthRepository): GetAuthTokenUseCase =
        GetAuthTokenUseCaseImpl(authRepository)

    @Provides
    fun provideGetSessionIdUseCase(authRepository: AuthRepository): GetSessionIdUseCase =
        GetSessionIdUseCaseImpl(authRepository)

    @Provides
    fun provideGetAccountIdUseCase(authRepository: AuthRepository): GetAccountIdUseCase =
        GetAccountIdUseCaseImpl(authRepository)

    @Provides
    fun provideLogOutUseCase(authRepository: AuthRepository): LogOutUseCase =
        LogOutUseCaseImpl(authRepository)

    // ShowList
    @Provides
    fun provideGetTvShowsUseCase(showsRepository: ShowsRepository): GetTvShowsUseCase =
        GetTvShowsUseCaseImpl(showsRepository)

    @Provides
    fun provideChangeFilterUseCase(showsRepository: ShowsRepository): ChangeFilterUseCase =
        ChangeFilterUseCaseImpl(showsRepository)

    @Provides
    fun provideSetInitialFilter(showsRepository: ShowsRepository): SetInitialFilter =
        SetInitialFilterImpl(showsRepository)

    // Details
    @Provides
    fun provideFetchDetailsUseCase(detailsRepository: DetailsRepository): FetchDetailsUseCase =
        FetchDetailsUseCaseImpl(detailsRepository)

    @Provides
    fun provideFetchCastListUseCase(detailsRepository: DetailsRepository): FetchCastListUseCase =
        FetchCastListUseCaseImpl(detailsRepository)

    @Provides
    fun provideGetCreditsUseCase(detailsRepository: DetailsRepository): GetCreditsUseCase =
        GetCreditsUseCaseImpl(detailsRepository)

    @Provides
    fun provideGetDetailsUseCase(detailsRepository: DetailsRepository): GetDetailsUseCase =
        GetDetailsUseCaseImpl(detailsRepository)

    // FavoriteShows
    @Provides
    fun providePutFavoriteUseCase(favoritesRepository: FavoritesRepository): PutFavoriteUseCase =
        PutFavoriteUseCaseImpl(favoritesRepository)

    @Provides
    fun provideRefreshFavoritesUseCase(favoritesRepository: FavoritesRepository): RefreshFavoritesUseCase =
        RefreshFavoritesUseCaseImpl(favoritesRepository)

    @Provides
    fun provideGetFavoritesUseCase(favoritesRepository: FavoritesRepository): GetFavoritesUseCase =
        GetFavoritesUseCaseImpl(favoritesRepository)

    // Seasons
    @Provides
    fun provideFetchSeasonsUseCase(seasonsRepository: SeasonsRepository): FetchSeasonsUseCase =
        FetchSeasonsUseCaseImpl(seasonsRepository)

    @Provides
    fun provideGetSeasonUseCase(seasonsRepository: SeasonsRepository): GetSeasonUseCase =
        GetSeasonUseCaseImpl(seasonsRepository)

    // Account
    @Provides
    fun provideGetAccountUseCase(accountRepository: AccountRepository): GetAccountUseCase =
        GetAccountUseCaseImpl(accountRepository)

    @Provides
    fun provideFetchAccountUseCase(accountRepository: AccountRepository): FetchAccountUseCase =
        FetchAccountUseCaseImpl(accountRepository)
}
