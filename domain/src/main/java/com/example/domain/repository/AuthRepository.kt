package com.example.domain.repository

import com.example.domain.model.RequestResult

interface AuthRepository {
    suspend fun getAuthToken(): RequestResult<String>
    suspend fun getSessionId(token: String): RequestResult<String>
    suspend fun getAccount(sessionID: String): RequestResult<Int>
    suspend fun logOutAccount(sessionID: String): RequestResult<Boolean>
}
