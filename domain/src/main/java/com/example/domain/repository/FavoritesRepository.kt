package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.RequestResult
import com.example.domain.model.TvShow

interface FavoritesRepository {
    suspend fun refreshFavoritesShows(
        accountId: Int,
        sessionId: String,
        page: Int
    ): RequestResult<Boolean>
    suspend fun getFavoritesShows(): LiveData<List<TvShow>>
    suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): RequestResult<Boolean>
}
