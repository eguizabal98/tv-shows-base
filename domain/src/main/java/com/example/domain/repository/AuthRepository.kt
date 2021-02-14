package com.example.domain.repository

import com.example.domain.model.NetworkResult

interface AuthRepository {
    suspend fun getAuthToken(): NetworkResult<String>
    suspend fun getSessionId(token: String): NetworkResult<String>
    suspend fun getAccountId(sessionID: String): NetworkResult<Int>
}