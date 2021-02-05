package com.example.data.network

import com.example.data.datamodels.network.SessionIdResponse
import com.example.data.datamodels.network.SessionRequestBody
import com.example.data.datamodels.network.TokenResponse
import retrofit2.http.*

interface AuthAPI {

    @GET(value = "authentication/token/new")
    suspend fun getAuthToken(@Query("api_key") apiKey: String): TokenResponse

    @POST(value = "authentication/session/new")
    suspend fun getSessionId(
        @Query("api_key") apiKey: String,
        @Body sessionRequestBody: SessionRequestBody
    ): SessionIdResponse

}
