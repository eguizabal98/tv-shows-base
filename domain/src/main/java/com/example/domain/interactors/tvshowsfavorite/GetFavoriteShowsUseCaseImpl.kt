package com.example.domain.interactors.tvshowsfavorite

import com.example.domain.model.TvShow
import com.example.domain.repository.ShowsFavoritesRepository

class GetFavoriteShowsUseCaseImpl(private val showsFavoritesRepository: ShowsFavoritesRepository) :
    GetFavoriteShowsUseCase {
    override suspend fun getFavoriteShows(): List<TvShow> =
        showsFavoritesRepository.getFavoritesShows()
}