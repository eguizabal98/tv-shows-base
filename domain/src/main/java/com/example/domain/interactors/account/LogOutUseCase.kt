package com.example.domain.interactors.account

import com.example.domain.model.RequestResult

interface LogOutUseCase {
    suspend fun logOutAccount(sessionID: String): RequestResult<Boolean>
}