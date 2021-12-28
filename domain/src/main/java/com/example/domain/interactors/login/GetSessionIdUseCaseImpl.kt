package com.example.domain.interactors.login

import com.example.domain.model.RequestResult
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class GetSessionIdUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) :
    GetSessionIdUseCase {
    override suspend fun getSessionId(token: String): RequestResult<String> =
        authRepository.getSessionId(token)
}
