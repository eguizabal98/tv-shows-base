package com.example.domain.interactors.favorite

import com.example.domain.model.RequestResult

interface RefreshFavoritesUseCase {
    suspend fun refreshFavoritesShows(
        accountId: Int,
        sessionId: String,
        page: Int
    ): RequestResult<Boolean>
}
