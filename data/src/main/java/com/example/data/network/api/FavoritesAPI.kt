package com.example.data.network.api

import com.example.data.di.NetworkingModule.API_KEY
import com.example.data.di.NetworkingModule.LANGUAGE
import com.example.data.di.NetworkingModule.SORT_VAL
import com.example.data.network.models.GeneralResponse
import com.example.data.network.models.showsdetail.FavoriteRequestBody
import com.example.data.network.models.showsdetail.FavoritesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoritesAPI {
    @POST(value = "account/{account_id}/favorite")
    suspend fun makeFavorite(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String,
        @Body favoriteRequestBody: FavoriteRequestBody
    ): GeneralResponse

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
