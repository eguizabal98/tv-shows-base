package com.example.domain.repository

import com.example.domain.model.NetworkResult
import com.example.domain.model.Profile

interface AccountRepository {
    suspend fun getAccountDetails(sessionId: String): NetworkResult<Profile>
}