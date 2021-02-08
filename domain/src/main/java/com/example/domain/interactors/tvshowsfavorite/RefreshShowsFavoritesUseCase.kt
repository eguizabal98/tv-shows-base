package com.example.domain.interactors.tvshowsfavorite

interface RefreshShowsFavoritesUseCase {
    suspend fun refreshFavoritesShows(accountId: Int, sessionId: String, page: Int)
}