package com.example.domain.interactors.login

import com.example.domain.model.RequestResult

interface GetSessionIdUseCase {
    suspend fun getSessionId(token: String): RequestResult<String>
}
