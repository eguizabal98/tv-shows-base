package com.example.data.di

import com.example.data.network.api.*
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "2615cf6e1170581121d16a6c7d7e4c9e"
const val LANGUAGE = "en-US"
const val SORT_VAL = "created_at.desc"

val networkingModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(AuthAPI::class.java) }
    single { get<Retrofit>().create(TvShowsAPI::class.java) }
    single { get<Retrofit>().create(DetailsAPI::class.java) }
    single { get<Retrofit>().create(FavoritesAPI::class.java) }
    single { get<Retrofit>().create(SeasonsAPI::class.java) }
}
