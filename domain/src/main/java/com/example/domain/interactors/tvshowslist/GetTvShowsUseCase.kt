package com.example.domain.interactors.tvshowslist

import com.example.domain.model.FilterType

interface GetTvShowsUseCase<out T> {
    fun getPopularShows(
        filterType: FilterType
    ): T

}