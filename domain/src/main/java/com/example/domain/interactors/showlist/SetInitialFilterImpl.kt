package com.example.domain.interactors.showlist

import com.example.domain.model.FilterType
import com.example.domain.repository.ShowsRepository

class SetInitialFilterImpl(private val showsRepository: ShowsRepository<Any>) : SetInitialFilter {
    override fun setInitialFilter(filterType: FilterType) =
        showsRepository.setInitialFilter(filterType)
}