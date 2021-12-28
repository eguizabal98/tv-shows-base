package com.example.domain.interactors.showlist

import com.example.domain.model.FilterType
import com.example.domain.repository.ShowsRepository
import javax.inject.Inject

class ChangeFilterUseCaseImpl @Inject constructor(private val showsRepository: ShowsRepository) :
    ChangeFilterUseCase {
    override fun changeFilter(filterType: FilterType) = showsRepository.changeFilter(filterType)
}
