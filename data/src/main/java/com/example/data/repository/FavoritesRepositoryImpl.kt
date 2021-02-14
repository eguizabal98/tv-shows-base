package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.network.api.FavoritesAPI
import com.example.data.network.models.showsdetail.FavoriteRequestBody
import com.example.data.util.mapFavoriteToShowDomain
import com.example.domain.model.RequestResult
import com.example.domain.model.TvShow
import com.example.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    private val favoritesAPI: FavoritesAPI,
    private val favoriteShowDao: FavoriteShowDao
) :
    FavoritesRepository<LiveData<List<TvShow>>>, BaseRepository() {

    override suspend fun refreshFavoritesShows(
        accountId: Int,
        sessionId: String,
        page: Int
    ): RequestResult<Boolean> {
        return fetchData(apiAction = {
            favoritesAPI.getFavorites(accountId = accountId, sessionId = sessionId, page = page)
        }, dbAction = {
            favoriteShowDao.clearBase()
            favoriteShowDao.insert(it.items)
        }, returnAction = { RequestResult.Success(true) })
    }

    override suspend fun getFavoritesShows(): LiveData<List<TvShow>> {
        val value = favoriteShowDao.getPopularShows()
        return Transformations.map(value) { it.mapFavoriteToShowDomain() }
    }

    override suspend fun putShowFavorite(
        favorite: Boolean,
        showId: Int,
        sessionId: String,
        accountId: Int
    ): RequestResult<Boolean> {
        return fetchData(apiAction = {
            favoritesAPI.makeFavorite(
                accountId = accountId,
                sessionId = sessionId,
                favoriteRequestBody = FavoriteRequestBody(mediaId = showId, favorite = favorite)
            )
        }, dbAction = {
            return@fetchData if (!favorite) {
                favoriteShowDao.delete(showId)
            } else {
                refreshFavoritesShows(accountId, sessionId, 1)
            }
        }, returnAction = { RequestResult.Success(favorite) })
    }
}