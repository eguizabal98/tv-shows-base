package com.example.domain.interactors.account

import com.example.domain.model.Profile
import com.example.domain.model.RequestResult

interface GetAccountUseCase {
    suspend fun getAccount(sessionId: String): RequestResult<Profile>
}
