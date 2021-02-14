package com.example.domain.repository

import com.example.domain.model.RequestResult

interface FavoritesRepository<out T> {
    suspend fun refreshFavoritesShows(
        accountId: Int,
        sessionId: String,
        page: Int
    ): RequestResult<Boolean>
    suspend fun getFavoritesShows(): T
    suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): RequestResult<Boolean>
}
