package com.example.domain.interactors.favorite

import androidx.lifecycle.LiveData
import com.example.domain.model.TvShow
import com.example.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCaseImpl @Inject constructor(private val favoritesRepository: FavoritesRepository) :
    GetFavoritesUseCase {
    override suspend fun getFavoriteShows(): LiveData<List<TvShow>> = favoritesRepository.getFavoritesShows()
}
