package com.example.data.network.api

import com.example.data.database.entities.ShowDetailEntity
import com.example.data.di.API_KEY
import com.example.data.di.LANGUAGE
import com.example.data.network.models.showsdetail.CreditsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowDetailsAPI {
    @GET(value = "tv/{tv_id}")
    suspend fun getShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): ShowDetailEntity

    @GET(value = "tv/{tv_id}/credits")
    suspend fun getCredits(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): CreditsResponse
}