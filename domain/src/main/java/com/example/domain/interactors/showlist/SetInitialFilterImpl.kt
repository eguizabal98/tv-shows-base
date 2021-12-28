package com.example.domain.interactors.showlist

import com.example.domain.model.FilterType
import com.example.domain.repository.ShowsRepository
import javax.inject.Inject

class SetInitialFilterImpl @Inject constructor(private val showsRepository: ShowsRepository) :
    SetInitialFilter {
    override fun setInitialFilter(filterType: FilterType) =
        showsRepository.setInitialFilter(filterType)
}
