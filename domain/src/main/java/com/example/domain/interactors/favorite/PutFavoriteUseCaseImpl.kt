package com.example.domain.interactors.favorite

import com.example.domain.repository.FavoritesRepository

class PutFavoriteUseCaseImpl(private val favoritesRepository: FavoritesRepository<Any>) :
    PutFavoriteUseCase {
    override suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ) = favoritesRepository.putShowFavorite(favorite, showId, sessionId, accountId)
}