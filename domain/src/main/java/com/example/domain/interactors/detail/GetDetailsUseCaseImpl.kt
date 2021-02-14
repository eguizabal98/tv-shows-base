package com.example.domain.interactors.detail

import com.example.domain.repository.DetailsRepository

class GetDetailsUseCaseImpl<out R>(private val detailsRepository: DetailsRepository<Any, R>) :
    GetDetailsUseCase<R> {
    override suspend fun getDetails(showId: Int): R = detailsRepository.getDetailsLocal(showId)
}