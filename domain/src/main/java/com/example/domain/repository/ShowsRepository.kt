package com.example.domain.repository

import com.example.domain.model.FilterType

interface ShowsRepository<out T> {
    fun getShows(): T
    fun changeFilter(filterType: FilterType)
    fun setInitialFilter(filterType: FilterType)
}
