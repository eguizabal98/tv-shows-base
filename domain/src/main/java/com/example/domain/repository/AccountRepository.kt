package com.example.domain.repository

import com.example.domain.model.Profile
import com.example.domain.model.RequestResult

interface AccountRepository {
    suspend fun getAccountDetails(sessionId: String): RequestResult<Profile>
    suspend fun fetchAccount(sessionID: String): RequestResult<Profile>
}
