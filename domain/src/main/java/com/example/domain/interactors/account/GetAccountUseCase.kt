package com.example.domain.interactors.account

import com.example.domain.model.RequestResult
import com.example.domain.model.Profile

interface GetAccountUseCase {
    suspend fun getAccount(sessionId: String): RequestResult<Profile>
}