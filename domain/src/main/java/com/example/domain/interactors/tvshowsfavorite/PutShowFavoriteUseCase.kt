package com.example.domain.interactors.tvshowsfavorite

import com.example.domain.model.NetworkResult

interface PutShowFavoriteUseCase {
    suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): NetworkResult<Boolean>
}