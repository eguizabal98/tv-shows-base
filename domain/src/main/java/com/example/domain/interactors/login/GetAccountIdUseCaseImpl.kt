package com.example.domain.interactors.login

import com.example.domain.model.NetworkResult
import com.example.domain.repository.AuthRepository

class GetAccountIdUseCaseImpl(private val authRepository: AuthRepository) : GetAccountIdUseCase {
    override suspend fun getAccountId(sessionID: String): NetworkResult<Int> =
        authRepository.getAccountId(sessionID)
}