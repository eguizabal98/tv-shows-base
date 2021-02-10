package com.example.domain.interactors.account

import com.example.domain.model.NetworkResult
import com.example.domain.repository.AuthRepository

class LogOutUseCaseImpl(private val authRepository: AuthRepository) : LogOutUseCase {
    override suspend fun logOutAccount(sessionID: String): NetworkResult<Boolean> =
        authRepository.logOutAccount(sessionID)
}