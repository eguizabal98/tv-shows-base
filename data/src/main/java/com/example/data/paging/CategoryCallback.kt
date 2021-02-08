package com.example.data.paging

import com.example.domain.model.FilterType

interface CategoryCallback {
    fun setInitialFilter(filterType: FilterType)
    fun onChange(filterType: FilterType)
}