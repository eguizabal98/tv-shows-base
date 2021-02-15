package com.example.domain.interactors.favorite

interface GetFavoritesUseCase<out T> {
    suspend fun getFavoriteShows(): T
}