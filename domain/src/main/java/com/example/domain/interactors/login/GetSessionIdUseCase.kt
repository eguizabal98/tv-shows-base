package com.example.domain.interactors.login

import com.example.domain.model.NetworkResult

interface GetSessionIdUseCase {
    suspend fun getSessionId(apiKey: String, token: String): NetworkResult<String>
}