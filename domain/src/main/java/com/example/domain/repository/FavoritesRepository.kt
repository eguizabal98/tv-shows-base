package com.example.domain.repository

import com.example.domain.model.RequestResult
import com.example.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun refreshFavoritesShows(
        accountId: Int,
        sessionId: String,
        page: Int
    ): RequestResult<Boolean>
    fun getFavoritesShows(): Flow<List<TvShow>>
    suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): RequestResult<Boolean>
}
