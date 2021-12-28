package com.example.data.di

import com.example.data.database.DataBase
import com.example.data.database.dao.AccountDao
import com.example.data.database.dao.CreditsDao
import com.example.data.database.dao.DetailDao
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.SeasonDao
import com.example.data.database.dao.TvShowDao
import com.example.data.network.api.AuthAPI
import com.example.data.network.api.DetailsAPI
import com.example.data.network.api.FavoritesAPI
import com.example.data.network.api.SeasonsAPI
import com.example.data.paging.ItemBoundaryCallBack
import com.example.data.repository.AccountRepositoryImpl
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.DetailsRepositoryImpl
import com.example.data.repository.FavoritesRepositoryImpl
import com.example.data.repository.SeasonsRepositoryImpl
import com.example.data.repository.ShowsRepositoryImpl
import com.example.data.util.Connectivity
import com.example.domain.repository.AccountRepository
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DetailsRepository
import com.example.domain.repository.FavoritesRepository
import com.example.domain.repository.SeasonsRepository
import com.example.domain.repository.ShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        authAPI: AuthAPI,
        accountDao: AccountDao,
        favoriteShowDao: FavoriteShowDao,
        tvShowDao: TvShowDao,
        connectivity: Connectivity,
        scope: CoroutineScope
    ): AuthRepository =
        AuthRepositoryImpl(authAPI, accountDao, favoriteShowDao, tvShowDao, connectivity, scope)

    @Provides
    fun provideShowsRepository(
        dataBase: DataBase,
        itemBoundaryCallBack: ItemBoundaryCallBack
    ): ShowsRepository =
        ShowsRepositoryImpl(dataBase, itemBoundaryCallBack)

    @Provides
    fun provideDetailsRepository(
        detailsAPI: DetailsAPI,
        detailDao: DetailDao,
        creditsDao: CreditsDao,
        connectivity: Connectivity,
        scope: CoroutineScope
    ): DetailsRepository =
        DetailsRepositoryImpl(detailsAPI, detailDao, creditsDao, connectivity, scope)

    @Provides
    fun provideFavoritesRepository(
        favoritesAPI: FavoritesAPI,
        favoriteShowDao: FavoriteShowDao,
        connectivity: Connectivity,
        scope: CoroutineScope
    ): FavoritesRepository =
        FavoritesRepositoryImpl(favoritesAPI, favoriteShowDao, connectivity, scope)

    @Provides
    fun provideSeasonsRepository(
        seasonsAPI: SeasonsAPI,
        seasonDao: SeasonDao,
        connectivity: Connectivity,
        scope: CoroutineScope
    ): SeasonsRepository =
        SeasonsRepositoryImpl(seasonsAPI, seasonDao, connectivity, scope)

    @Provides
    fun provideAccountRepository(
        accountDao: AccountDao,
        authAPI: AuthAPI,
        connectivity: Connectivity,
        scope: CoroutineScope
    ): AccountRepository =
        AccountRepositoryImpl(accountDao, authAPI, connectivity, scope)
}
