package com.example.domain.interactors.favorite

import com.example.domain.model.RequestResult

interface PutFavoriteUseCase {
    suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): RequestResult<Boolean>
}
