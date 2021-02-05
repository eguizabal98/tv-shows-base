package com.example.domain.interactors.tvshowslist

import com.example.domain.model.FilterType
import com.example.domain.repository.ShowsRepository

class GetTvShowsUseCaseImpl<T : Any>(private val showsRepository: ShowsRepository<T>) :
    GetTvShowsUseCase<T> {

    override fun getPopularShows(filterType: FilterType): T {
        return showsRepository.getPopularShows(filterType)
    }

}