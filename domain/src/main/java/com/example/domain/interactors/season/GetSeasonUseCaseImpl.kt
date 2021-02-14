package com.example.domain.interactors.season

import com.example.domain.repository.SeasonsRepository

class GetSeasonUseCaseImpl<out T>(private val seasonsRepository: SeasonsRepository<T>) :
    GetSeasonUseCase<T> {
    override suspend fun getSeasons(showsId: Int): T = seasonsRepository.getSeasons(showsId)
}