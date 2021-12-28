package com.example.domain.interactors.detail

import androidx.lifecycle.LiveData
import com.example.domain.model.Cast
import com.example.domain.repository.DetailsRepository
import javax.inject.Inject

class GetCreditsUseCaseImpl @Inject constructor(private val detailsRepository: DetailsRepository) :
    GetCreditsUseCase {

    override suspend fun getCredits(showId: Int): LiveData<List<Cast>> =
        detailsRepository.getCreditsLocal(showId)
}
