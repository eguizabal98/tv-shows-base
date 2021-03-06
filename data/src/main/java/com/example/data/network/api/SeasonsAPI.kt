package com.example.data.network.api

import com.example.data.database.entities.SeasonEntity
import com.example.data.di.API_KEY
import com.example.data.di.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeasonsAPI {
    @GET(value = "tv/{tv_id}/season/{season_number}")
    suspend fun getSeasons(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): SeasonEntity
}