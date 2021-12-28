package com.example.domain.interactors.detail

import com.example.domain.repository.DetailsRepository
import javax.inject.Inject

class GetDetailsUseCaseImpl @Inject constructor(private val detailsRepository: DetailsRepository) :
    GetDetailsUseCase {
    override suspend fun getDetails(showId: Int) = detailsRepository.getDetailsLocal(showId)
}
