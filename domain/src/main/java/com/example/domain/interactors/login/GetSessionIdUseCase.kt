package com.example.domain.interactors.login

import com.example.domain.model.NetworkResult

interface GetSessionIdUseCase {
    suspend fun getSessionId(token: String): NetworkResult<String>
}