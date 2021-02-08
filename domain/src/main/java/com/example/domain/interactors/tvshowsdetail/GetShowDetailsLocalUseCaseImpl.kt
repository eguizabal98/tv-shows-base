package com.example.domain.interactors.tvshowsdetail

import com.example.domain.model.ShowDetails
import com.example.domain.repository.ShowDetailsRepository

class GetShowDetailsLocalUseCaseImpl(private val showDetailsRepository: ShowDetailsRepository) :
    GetShowDetailsLocalUseCase {
    override suspend fun getShowDetails(showId: Int): ShowDetails =
        showDetailsRepository.getShowDetailsLocal(showId)
}