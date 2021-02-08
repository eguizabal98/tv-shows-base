package com.example.data.network.api

import com.example.data.di.API_KEY
import com.example.data.di.LANGUAGE
import com.example.data.di.SORT_VAL
import com.example.data.network.models.showsdetail.FavoriteRequestBody
import com.example.data.network.models.showsdetail.FavoritesResponse
import retrofit2.http.*

interface FavoriteShowAPI {
    @POST(value = "account/{account_id}/favorite")
    suspend fun makeFavorite(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String,
        @Body favoriteRequestBody: FavoriteRequestBody
    )

    @GET(value = "account/{account_id}/favorite/tv")
    suspend fun getFavorites(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String = SORT_VAL,
        @Query("page") page: Int
    ): FavoritesResponse
}