package com.example.domain.interactors.login

import com.example.domain.model.RequestResult

interface GetAccountIdUseCase {
    suspend fun getAccountId(sessionID: String): RequestResult<Int>
}