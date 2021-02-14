package com.example.domain.interactors.detail

import com.example.domain.repository.DetailsRepository

class GetCreditsUseCaseImpl<T : Any>(private val detailsRepository: DetailsRepository<T, Any>) :
    GetCreditsUseCase<T> {

    override suspend fun getCredits(showId: Int): T =
        detailsRepository.getCreditsLocal(showId)

}