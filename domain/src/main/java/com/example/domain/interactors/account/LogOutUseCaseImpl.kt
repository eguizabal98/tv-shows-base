package com.example.domain.interactors.account

import com.example.domain.model.RequestResult
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) :
    LogOutUseCase {
    override suspend fun logOutAccount(sessionID: String): RequestResult<Boolean> =
        authRepository.logOutAccount(sessionID)
}
