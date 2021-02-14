package com.example.domain.interactors.detail

import com.example.domain.model.RequestResult
import com.example.domain.repository.DetailsRepository

class FetchCastListUseCaseImpl(private val detailsRepository: DetailsRepository<Any, Any>) :
    FetchCastListUseCase {
    override suspend fun fetchCast(showId: Int): RequestResult<Boolean> =
        detailsRepository.fetchCredits(showId)
}