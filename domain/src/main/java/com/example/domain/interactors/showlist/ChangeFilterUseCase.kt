package com.example.domain.interactors.showlist

import com.example.domain.model.FilterType

interface ChangeFilterUseCase {
    fun changeFilter(filterType: FilterType)
}
