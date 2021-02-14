package com.example.domain.repository

import com.example.domain.model.RequestResult
import com.example.domain.model.Profile

interface AccountRepository {
    suspend fun getAccountDetails(sessionId: String): RequestResult<Profile>
    suspend fun fetchAccount(sessionID: String): RequestResult<Profile>
}
