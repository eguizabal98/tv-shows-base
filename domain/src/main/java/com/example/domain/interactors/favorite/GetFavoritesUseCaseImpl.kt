package com.example.domain.interactors.favorite

import com.example.domain.repository.FavoritesRepository

class GetFavoritesUseCaseImpl<T : Any>(private val favoritesRepository: FavoritesRepository<T>) :
    GetFavoritesUseCase<T> {
    override suspend fun getFavoriteShows(): T = favoritesRepository.getFavoritesShows()
}