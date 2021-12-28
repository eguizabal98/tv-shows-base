package com.example.domain.interactors.season

import com.example.domain.model.RequestResult
import com.example.domain.repository.SeasonsRepository
import javax.inject.Inject

class FetchSeasonsUseCaseImpl @Inject constructor(private val seasonsRepository: SeasonsRepository) :
    FetchSeasonsUseCase {
    override suspend fun getSeasons(showsId: Int, seasons: Int): RequestResult<Boolean> =
        seasonsRepository.fetchSeasons(showsId, seasons)
}
