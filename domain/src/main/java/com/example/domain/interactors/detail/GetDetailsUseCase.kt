package com.example.domain.interactors.detail

interface GetDetailsUseCase<out R> {
    suspend fun getDetails(showId: Int): R
}