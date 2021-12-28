package com.example.domain.interactors.favorite

import com.example.domain.repository.FavoritesRepository
import javax.inject.Inject

class PutFavoriteUseCaseImpl @Inject constructor(private val favoritesRepository: FavoritesRepository) :
    PutFavoriteUseCase {
    override suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ) = favoritesRepository.putShowFavorite(favorite, showId, sessionId, accountId)
}
