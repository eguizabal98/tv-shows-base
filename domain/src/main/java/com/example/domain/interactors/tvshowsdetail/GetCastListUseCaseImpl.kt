package com.example.domain.interactors.tvshowsdetail

import com.example.domain.model.Cast
import com.example.domain.repository.ShowDetailsRepository

class GetCastListUseCaseImpl(private val showDetailsRepository: ShowDetailsRepository) :
    GetCastListUseCase {
    override suspend fun getCast(showId: Int): List<Cast> = showDetailsRepository.getCredits(showId)
}