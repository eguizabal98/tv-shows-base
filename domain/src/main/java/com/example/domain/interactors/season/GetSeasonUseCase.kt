package com.example.domain.interactors.season

interface GetSeasonUseCase<out T> {
    suspend fun getSeasons(showsId: Int): T
}