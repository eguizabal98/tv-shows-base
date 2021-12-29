package com.example.domain.interactors.season

import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import com.example.domain.repository.SeasonsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSeasonUseCaseImpl @Inject constructor(private val seasonsRepository: SeasonsRepository) :
    GetSeasonUseCase {
    override fun getSeasons(showsId: Int, seasons: Int): Flow<RequestResult<List<Season>?>> =
        seasonsRepository.getSeasons(showsId, seasons)
}
