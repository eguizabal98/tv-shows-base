package com.example.domain.interactors.showlist

interface GetTvShowsUseCase<out T> {
    fun getPopularShows(): T
}