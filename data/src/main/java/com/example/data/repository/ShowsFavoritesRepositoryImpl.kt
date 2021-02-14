package com.example.data.repository

import android.util.Log
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.network.api.FavoriteShowAPI
import com.example.data.network.models.showsdetail.FavoriteRequestBody
import com.example.data.util.mapFavoriteToShowDomain
import com.example.domain.model.NetworkResult
import com.example.domain.model.TvShow
import com.example.domain.repository.ShowsFavoritesRepository

class ShowsFavoritesRepositoryImpl(
    private val favoriteShowAPI: FavoriteShowAPI,
    private val favoriteShowDao: FavoriteShowDao
) :
    ShowsFavoritesRepository {

    override suspend fun refreshFavoritesShows(accountId: Int, sessionId: String, page: Int) {
        try {
            Log.d("FavoritesRepo", accountId.toString())
            Log.d("FavoritesRepo", sessionId)
            Log.d("FavoritesRepo", page.toString())
            val response = favoriteShowAPI.getFavorites(
                accountId = accountId,
                sessionId = sessionId,
                page = page
            )
            favoriteShowDao.clearBase()
            favoriteShowDao.insert(response.items)
            Log.d("FavoritesRepo", response.toString())
            val value = favoriteShowDao.getPopularShows()
            Log.d("FavoritesRepo", value.toString())

        } catch (e: Exception) {
            Log.e("FavoritesRepo", e.toString())
            Log.e("FavoritesRepo", e.message.toString())
            Log.e("FavoritesRepo", e.cause.toString())
        }
    }

    override suspend fun getFavoritesShows(): List<TvShow> {
        val value = favoriteShowDao.getPopularShows()
        return value.mapFavoriteToShowDomain()
    }

    override suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): NetworkResult<Boolean> {
        favoriteShowAPI.makeFavorite(
            accountId = accountId,
            sessionId = sessionId,
            favoriteRequestBody = FavoriteRequestBody(mediaId = showId, favorite = favorite)
        )
        if (!favorite) {
            favoriteShowDao.delete(showId)
        }else{
            refreshFavoritesShows(accountId,sessionId,1)
        }

        return NetworkResult.Success(favorite)
    }
}