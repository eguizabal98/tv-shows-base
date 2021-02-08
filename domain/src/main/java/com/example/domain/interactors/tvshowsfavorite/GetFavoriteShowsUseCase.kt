package com.example.domain.interactors.tvshowsfavorite

import com.example.domain.model.TvShow

interface GetFavoriteShowsUseCase {
    suspend fun getFavoriteShows(): List<TvShow>
}