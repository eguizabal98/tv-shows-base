package com.example.domain.interactors.favorite

import com.example.domain.repository.FavoritesRepository
import javax.inject.Inject

class RefreshFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) :
    RefreshFavoritesUseCase {
    override suspend fun refreshFavoritesShows(accountId: Int, sessionId: String, page: Int) =
        favoritesRepository.refreshFavoritesShows(accountId, sessionId, page)
}
