package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.DataBase
import com.example.data.database.dao.AccountDao
import com.example.data.database.dao.CreditsDao
import com.example.data.database.dao.DetailDao
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.SeasonDao
import com.example.data.database.dao.TvShowDao
import com.example.data.network.api.TvShowsAPI
import com.example.data.paging.ItemBoundaryCallBack
import com.example.data.secure.KeyManagerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private const val DB_NAME = "TvShowsDb"

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): DataBase =
        Room.databaseBuilder(context, DataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .openHelperFactory(KeyManagerRepository(context).getCypherFactory())
            .build()

    @Provides
    fun provideTvShowDao(dataBase: DataBase): TvShowDao = dataBase.tvShowDao()

    @Provides
    fun provideFavoriteShowDao(dataBase: DataBase): FavoriteShowDao = dataBase.favoriteShowDao()

    @Provides
    fun provideAccountDao(dataBase: DataBase): AccountDao = dataBase.accountDao()

    @Provides
    fun provideDetailsDao(dataBase: DataBase): DetailDao = dataBase.detailsDao()

    @Provides
    fun provideCreditsDao(dataBase: DataBase): CreditsDao = dataBase.creditsDao()

    @Provides
    fun provideSeasonDao(dataBase: DataBase): SeasonDao = dataBase.seasonDao()

    @Provides
    fun provideItemBoundaryCallBack(
        tvShowsAPI: TvShowsAPI,
        dataBase: DataBase,
        scope: CoroutineScope
    ): ItemBoundaryCallBack =
        ItemBoundaryCallBack(tvShowsAPI, dataBase, scope)

    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}
