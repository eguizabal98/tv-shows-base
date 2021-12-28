package com.example.domain.interactors.login

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthTokenUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) :
    GetAuthTokenUseCase {
    override suspend fun getAuthToken() = authRepository.getAuthToken()
}
