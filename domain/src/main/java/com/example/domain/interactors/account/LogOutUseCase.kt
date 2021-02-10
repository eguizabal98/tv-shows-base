package com.example.domain.interactors.account

import com.example.domain.model.NetworkResult

interface LogOutUseCase {
    suspend fun logOutAccount(sessionID: String): NetworkResult<Boolean>
}