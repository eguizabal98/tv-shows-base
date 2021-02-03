package com.example.data.network

import com.example.data.network.requestModels.SessionIdResponse
import com.example.data.network.requestModels.SessionRequestBody
import com.example.data.network.requestModels.TokenResponse
import retrofit2.http.*

interface NetworkAPI {

    @GET(value = "authentication/token/new?")
    suspend fun getAuthToken(@Query("api_key") apiKey: String): TokenResponse

    @POST(value = "authentication/session/new")
    suspend fun getSessionId(
        @Query("api_key") apiKey: String,
        @Body sessionRequestBody: SessionRequestBody
    ): SessionIdResponse

}
