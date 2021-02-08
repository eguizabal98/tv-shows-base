package com.example.data.network.API

import com.example.data.networkmodels.authentication.SessionIdResponse
import com.example.data.networkmodels.authentication.SessionRequestBody
import com.example.data.networkmodels.authentication.TokenResponse
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
