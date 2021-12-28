package com.example.domain.interactors.detail

import com.example.domain.model.RequestResult
import com.example.domain.repository.DetailsRepository
import javax.inject.Inject

class FetchDetailsUseCaseImpl @Inject constructor(private val detailsRepository: DetailsRepository) :
    FetchDetailsUseCase {
    override suspend fun getShowDetails(showId: Int): RequestResult<Boolean> =
        detailsRepository.fetchDetails(showId)
}
