package com.example.domain.interactors.favorite

import androidx.lifecycle.LiveData
import com.example.domain.model.TvShow

interface GetFavoritesUseCase {
    suspend fun getFavoriteShows(): LiveData<List<TvShow>>
}
