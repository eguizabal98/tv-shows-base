package com.example.domain.interactors.detail

import com.example.domain.model.Cast
import com.example.domain.model.RequestResult
import com.example.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCreditsUseCaseImpl @Inject constructor(private val detailsRepository: DetailsRepository) :
    GetCreditsUseCase {

    override fun getCredits(showId: Int): Flow<RequestResult<List<Cast>?>> =
        detailsRepository.getCreditsLocal(showId)
}
