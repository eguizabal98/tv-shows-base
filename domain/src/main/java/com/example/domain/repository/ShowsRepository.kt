package com.example.domain.repository


import com.example.domain.model.FilterType

interface ShowsRepository<out T> {
    fun getPopularShows(
        filterType: FilterType
    ): T
}