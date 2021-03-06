package com.example.data.network.api

import com.example.data.database.entities.AccountEntity
import com.example.data.di.API_KEY
import com.example.data.network.models.authentication.LogOutBody
import com.example.data.network.models.authentication.SessionIdResponse
import com.example.data.network.models.authentication.SessionRequestBody
import com.example.data.network.models.authentication.TokenResponse
import com.example.data.network.models.GeneralResponse
import retrofit2.http.*

interface AuthAPI {

    @GET(value = "authentication/token/new")
    suspend fun getAuthToken(@Query("api_key") apiKey: String = API_KEY): TokenResponse

    @POST(value = "authentication/session/new")
    suspend fun getSessionId(
        @Query("api_key") apiKey: String = API_KEY,
        @Body sessionRequestBody: SessionRequestBody
    ): SessionIdResponse

    @GET(value = "account")
    suspend fun getAccountId(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session: String
    ): AccountEntity

    @HTTP(method = "DELETE",path = "authentication/session",hasBody = true)
    suspend fun logOut(
        @Query("api_key") apiKey: String = API_KEY,
        @Body logOutBody: LogOutBody
    ): GeneralResponse

}
