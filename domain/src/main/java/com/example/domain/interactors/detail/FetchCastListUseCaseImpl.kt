package com.example.domain.interactors.detail

import com.example.domain.model.RequestResult
import com.example.domain.repository.DetailsRepository
import javax.inject.Inject

class FetchCastListUseCaseImpl @Inject constructor(private val detailsRepository: DetailsRepository) :
    FetchCastListUseCase {
    override suspend fun fetchCast(showId: Int): RequestResult<Boolean> =
        detailsRepository.fetchCredits(showId)
}
