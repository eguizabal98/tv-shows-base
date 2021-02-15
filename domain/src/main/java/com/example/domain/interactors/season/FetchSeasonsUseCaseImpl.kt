package com.example.domain.interactors.season

import com.example.domain.model.RequestResult
import com.example.domain.repository.SeasonsRepository

class FetchSeasonsUseCaseImpl(private val seasonsRepository: SeasonsRepository<Any>) :
    FetchSeasonsUseCase {
    override suspend fun getSeasons(showsId: Int, seasons: Int): RequestResult<Boolean> =
        seasonsRepository.fetchSeasons(showsId, seasons)
}