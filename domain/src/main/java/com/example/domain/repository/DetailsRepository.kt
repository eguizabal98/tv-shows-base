package com.example.domain.repository

import com.example.domain.model.RequestResult

interface DetailsRepository<out T, out R> {
    suspend fun fetchDetails(showId: Int): RequestResult<Boolean>
    suspend fun getDetailsLocal(showId: Int): R
    suspend fun fetchCredits(showId: Int): RequestResult<Boolean>
    suspend fun getCreditsLocal(showId: Int): T
}
