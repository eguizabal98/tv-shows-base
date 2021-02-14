package com.example.domain.interactors.favorite

import com.example.domain.repository.FavoritesRepository

class RefreshFavoritesUseCaseImpl(
    private val favoritesRepository: FavoritesRepository<Any>
) :
    RefreshFavoritesUseCase {
    override suspend fun refreshFavoritesShows(accountId: Int, sessionId: String, page: Int) =
        favoritesRepository.refreshFavoritesShows(accountId, sessionId, page)
}
