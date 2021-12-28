package com.example.tvshowsbase.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.util.Connectivity
import com.example.data.util.ConnectivityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PresenterModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("sessionInfo", Context.MODE_PRIVATE)

    @Provides
    fun provideConnectivity(@ApplicationContext context: Context): Connectivity =
        ConnectivityImpl(context)
}
