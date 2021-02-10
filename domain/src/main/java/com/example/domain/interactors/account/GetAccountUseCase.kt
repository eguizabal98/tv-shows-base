package com.example.domain.interactors.account

import com.example.domain.model.NetworkResult
import com.example.domain.model.Profile

interface GetAccountUseCase {
    suspend fun getAccount(sessionId: String): NetworkResult<Profile>
}