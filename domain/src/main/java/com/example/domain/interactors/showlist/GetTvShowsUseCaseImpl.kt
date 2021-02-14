package com.example.domain.interactors.showlist

import com.example.domain.repository.ShowsRepository

class GetTvShowsUseCaseImpl<T : Any>(private val showsRepository: ShowsRepository<T>) :
    GetTvShowsUseCase<T> {
    override fun getPopularShows(): T {
        return showsRepository.getPopularShows()
    }
}