package com.example.domain.interactors.showlist

import com.example.domain.model.FilterType

interface SetInitialFilter {
    fun setInitialFilter(filterType: FilterType)
}