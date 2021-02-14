package com.example.domain.repository

import com.example.domain.model.FilterType

interface ShowsRepository<out T> {
    fun getPopularShows(): T
    fun changeFilter(filterType: FilterType)
    fun setInitialFilter(filterType: FilterType)
}
