package com.example.domain.interactors.tvshowsfavorite

import com.example.domain.repository.ShowsFavoritesRepository

class PutShowFavoriteUseCaseImpl(private val showsFavoritesRepository: ShowsFavoritesRepository) :
    PutShowFavoriteUseCase {
    override suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ) = showsFavoritesRepository.putShowFavorite(favorite, showId, sessionId, accountId)
}