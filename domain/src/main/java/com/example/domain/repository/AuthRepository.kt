package com.example.domain.repository

import com.example.domain.model.NetworkResult

interface AuthRepository {
    suspend fun getAuthToken(apiKey: String): NetworkResult<String>
    suspend fun getSessionId(apiKey: String, token: String): NetworkResult<String>
}