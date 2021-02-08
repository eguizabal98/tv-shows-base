package com.example.domain.repository

import com.example.domain.model.NetworkResult
import com.example.domain.model.TvShow

interface ShowsFavoritesRepository {
    suspend fun refreshFavoritesShows(accountId: Int, sessionId: String, page: Int)
    suspend fun getFavoritesShows(): List<TvShow>
    suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): NetworkResult<Boolean>
}