package com.example.domain.interactors.login

import com.example.domain.model.NetworkResult

interface GetAuthTokenUseCase {
    suspend fun getAuthToken(apiKey: String): NetworkResult<String>
}