package com.example.domain.interactors.login

import com.example.domain.model.NetworkResult
import com.example.domain.repository.AuthRepository

class GetSessionIdUseCaseImpl(private val authRepository: AuthRepository) : GetSessionIdUseCase {
    override suspend fun getSessionId(token: String): NetworkResult<String> =
        authRepository.getSessionId(token)
}