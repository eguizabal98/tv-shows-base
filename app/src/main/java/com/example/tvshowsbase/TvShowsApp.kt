package com.example.tvshowsbase

import android.app.Application
import com.example.data.di.networkingModule
import com.example.data.di.repositoryModule
import com.example.domain.di.interactsModule
import com.example.tvshowsbase.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class TvShowsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TvShowsApp)
            modules(provideDependency())
        }
    }

    private val appComponent = appModules + domainModules + dataModules
    private fun provideDependency() = appComponent
}

val appModules = listOf(presenterModule)
val domainModules = listOf(interactsModule)
val dataModules = listOf(networkingModule, repositoryModule)
