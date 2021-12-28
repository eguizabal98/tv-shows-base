package com.example.data.di

import com.example.data.network.RemoteDataSource
import com.example.data.network.api.AuthAPI
import com.example.data.network.api.DetailsAPI
import com.example.data.network.api.FavoritesAPI
import com.example.data.network.api.SeasonsAPI
import com.example.data.network.api.TvShowsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    const val API_KEY = "2615cf6e1170581121d16a6c7d7e4c9e"
    const val LANGUAGE = "en-US"
    const val SORT_VAL = "created_at.desc"

    @Singleton
    @Provides
    fun provideAuthAPI(
        remoteDataSource: RemoteDataSource
    ): AuthAPI = remoteDataSource.buildApi(AuthAPI::class.java)

    @Singleton
    @Provides
    fun provideTvShowsAPI(
        remoteDataSource: RemoteDataSource
    ): TvShowsAPI = remoteDataSource.buildApi(TvShowsAPI::class.java)

    @Singleton
    @Provides
    fun provideDetailsAPI(
        remoteDataSource: RemoteDataSource
    ): DetailsAPI = remoteDataSource.buildApi(DetailsAPI::class.java)

    @Singleton
    @Provides
    fun provideFavoritesAPI(
        remoteDataSource: RemoteDataSource
    ): FavoritesAPI = remoteDataSource.buildApi(FavoritesAPI::class.java)

    @Singleton
    @Provides
    fun provideSeasonsAPI(
        remoteDataSource: RemoteDataSource
    ): SeasonsAPI = remoteDataSource.buildApi(SeasonsAPI::class.java)
}
