package com.example.data.network.api

import com.example.data.network.models.showslist.TvShowsResult
import com.example.data.di.API_KEY
import com.example.data.di.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowsAPI {
    @GET(value = "tv/popular")
    suspend fun getPopularShows(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): TvShowsResult

    @GET(value = "tv/top_rated")
    suspend fun getTopRateShows(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): TvShowsResult

    @GET(value = "tv/on_the_air")
    suspend fun getOnAirShows(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): TvShowsResult

    @GET(value = "tv/airing_today")
    suspend fun getAiringTodayShows(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): TvShowsResult
}