package com.example.domain.interactors.detail

interface GetCreditsUseCase<out T> {
    suspend fun getCredits(showId: Int): T
}