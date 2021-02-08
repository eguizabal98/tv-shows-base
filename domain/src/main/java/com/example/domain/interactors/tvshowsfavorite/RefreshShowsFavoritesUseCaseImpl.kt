package com.example.domain.interactors.tvshowsfavorite

import com.example.domain.repository.ShowsFavoritesRepository

class RefreshShowsFavoritesUseCaseImpl(
    private val showsFavoritesRepository: ShowsFavoritesRepository
) :
    RefreshShowsFavoritesUseCase {

    override suspend fun refreshFavoritesShows(accountId: Int, sessionId: String, page: Int) =
        showsFavoritesRepository.refreshFavoritesShows(accountId, sessionId, page)

}
