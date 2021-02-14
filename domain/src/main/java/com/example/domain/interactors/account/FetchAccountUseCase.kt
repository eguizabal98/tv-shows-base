package com.example.domain.interactors.account

import com.example.domain.model.Profile
import com.example.domain.model.RequestResult

interface FetchAccountUseCase {
    suspend fun fetchAccount(sessionID: String): RequestResult<Profile>
}