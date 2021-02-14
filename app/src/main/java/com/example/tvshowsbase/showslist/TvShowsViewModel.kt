package com.example.tvshowsbase.showslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.example.domain.interactors.tvshowsfavorite.RefreshShowsFavoritesUseCase
import com.example.domain.interactors.tvshowslist.GetTvShowsUseCase
import com.example.domain.model.FilterType
import com.example.domain.model.TvShow
import kotlinx.coroutines.launch

class TvShowsViewModel(
    getTvShowsUseCase: GetTvShowsUseCase<LiveData<PagedList<TvShow>>>,
    private val refreshShowsFavoritesUseCase: RefreshShowsFavoritesUseCase
) :
    ViewModel() {

    private var firstCall = true

    val showsList: LiveData<PagedList<TvShow>> =
        getTvShowsUseCase.getPopularShows(FilterType.TOP_RATE)

    fun getFavorites(accountId: Int, sessionId: String, page: Int) {
        if (firstCall) {
            viewModelScope.launch {
                refreshShowsFavoritesUseCase.refreshFavoritesShows(accountId, sessionId, page)
            }
            firstCall = false
        }
    }

}