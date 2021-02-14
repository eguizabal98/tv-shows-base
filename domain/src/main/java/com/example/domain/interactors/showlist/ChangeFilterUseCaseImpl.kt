package com.example.domain.interactors.showlist

import com.example.domain.model.FilterType
import com.example.domain.repository.ShowsRepository

class ChangeFilterUseCaseImpl(private val showsRepository: ShowsRepository<Any>) :
    ChangeFilterUseCase {
    override fun changeFilter(filterType: FilterType) = showsRepository.changeFilter(filterType)
}