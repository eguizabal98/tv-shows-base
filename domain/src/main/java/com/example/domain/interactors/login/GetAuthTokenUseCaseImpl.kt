package com.example.domain.interactors.login

import com.example.domain.repository.AuthRepository

class GetAuthTokenUseCaseImpl(private val authRepository: AuthRepository) : GetAuthTokenUseCase {
    override suspend fun getAuthToken(apiKey: String) = authRepository.getAuthToken(apiKey)
}