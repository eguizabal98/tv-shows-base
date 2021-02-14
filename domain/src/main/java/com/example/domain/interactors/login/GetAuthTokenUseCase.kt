package com.example.domain.interactors.login

import com.example.domain.model.RequestResult

interface GetAuthTokenUseCase {
    suspend fun getAuthToken(): RequestResult<String>
}