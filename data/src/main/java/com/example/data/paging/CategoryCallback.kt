package com.example.data.paging

import com.example.domain.model.FilterType

interface CategoryCallback {
    fun onChange(filterType: FilterType)
}