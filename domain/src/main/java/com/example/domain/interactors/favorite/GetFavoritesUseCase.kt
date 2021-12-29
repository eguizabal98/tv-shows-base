package com.example.domain.interactors.favorite

import com.example.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface GetFavoritesUseCase {
    fun getFavoriteShows(): Flow<List<TvShow>>
}
