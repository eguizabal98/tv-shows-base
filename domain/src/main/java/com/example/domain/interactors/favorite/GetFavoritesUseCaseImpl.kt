package com.example.domain.interactors.favorite

import com.example.domain.model.TvShow
import com.example.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCaseImpl @Inject constructor(private val favoritesRepository: FavoritesRepository) :
    GetFavoritesUseCase {
    override fun getFavoriteShows(): Flow<List<TvShow>> = favoritesRepository.getFavoritesShows()
}
