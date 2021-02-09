package com.example.domain.interactors.season

import com.example.domain.model.NetworkResult
import com.example.domain.model.Season
import com.example.domain.repository.SeasonsRepository

class GetSeasonsUseCaseImpl(private val seasonsRepository: SeasonsRepository) : GetSeasonsUseCase {
    override suspend fun getSeasons(showsId: Int, seasons: Int): NetworkResult<List<Season>> =
        seasonsRepository.getSeasons(showsId, seasons)
}