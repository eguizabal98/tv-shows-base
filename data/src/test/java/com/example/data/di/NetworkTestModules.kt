package com.example.data.di

import com.example.data.network.api.AuthAPI
import com.example.data.network.api.DetailsAPI
import com.example.data.network.api.FavoritesAPI
import com.example.data.network.api.SeasonsAPI
import com.example.data.network.api.TvShowsAPI
import com.example.data.util.Connectivity
import com.example.data.util.ConnectivityImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.mockwebserver.MockWebServer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun configureNetworkModuleForTest(baseApi: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(AuthAPI::class.java) }
    single { get<Retrofit>().create(TvShowsAPI::class.java) }
    single { get<Retrofit>().create(DetailsAPI::class.java) }
    single { get<Retrofit>().create(FavoritesAPI::class.java) }
    single { get<Retrofit>().create(SeasonsAPI::class.java) }

    factory<Connectivity> { ConnectivityImpl(context = androidContext()) }

    factory { CoroutineScope(Dispatchers.IO) }
}

val mockWebServerTest = module {
    factory {
        MockWebServer()
    }
}
