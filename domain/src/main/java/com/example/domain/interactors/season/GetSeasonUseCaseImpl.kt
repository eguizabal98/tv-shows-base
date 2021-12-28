package com.example.domain.interactors.season

import androidx.lifecycle.LiveData
import com.example.domain.model.Season
import com.example.domain.repository.SeasonsRepository
import javax.inject.Inject

class GetSeasonUseCaseImpl @Inject constructor(private val seasonsRepository: SeasonsRepository) :
    GetSeasonUseCase {
    override suspend fun getSeasons(showsId: Int): LiveData<List<Season>> = seasonsRepository.getSeasons(showsId)
}
