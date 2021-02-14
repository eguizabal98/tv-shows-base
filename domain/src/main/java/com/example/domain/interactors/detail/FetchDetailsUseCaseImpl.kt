package com.example.domain.interactors.detail

import com.example.domain.model.RequestResult
import com.example.domain.repository.DetailsRepository

class FetchDetailsUseCaseImpl(private val detailsRepository: DetailsRepository<Any, Any>) :
    FetchDetailsUseCase {
    override suspend fun getShowDetails(showId: Int): RequestResult<Boolean> =
        detailsRepository.fetchDetails(showId)
}