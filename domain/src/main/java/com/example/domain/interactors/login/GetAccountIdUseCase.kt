package com.example.domain.interactors.login

import com.example.domain.model.NetworkResult

interface GetAccountIdUseCase {
    suspend fun getAccountId(sessionID: String): NetworkResult<Int>
}