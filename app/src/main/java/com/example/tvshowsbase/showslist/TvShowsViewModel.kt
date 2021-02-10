package com.example.tvshowsbase.showslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.example.domain.interactors.account.LogOutUseCase
import com.example.domain.interactors.tvshowsfavorite.RefreshShowsFavoritesUseCase
import com.example.domain.interactors.tvshowslist.GetTvShowsUseCase
import com.example.domain.model.FilterType
import com.example.domain.model.NetworkResult
import com.example.domain.model.TvShow
import com.example.domain.model.WorkState
import kotlinx.coroutines.launch

class TvShowsViewModel(
    getTvShowsUseCase: GetTvShowsUseCase<LiveData<PagedList<TvShow>>>,
    private val refreshShowsFavoritesUseCase: RefreshShowsFavoritesUseCase,
    private val logOutUseCase: LogOutUseCase
) :
    ViewModel() {

    private var firstCall = true

    private val _logOutState = MutableLiveData<WorkState<Boolean>>()
    val logOutState: LiveData<WorkState<Boolean>>
        get() = _logOutState

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

    fun logOut(sessionId: String) {
        viewModelScope.launch {
            _logOutState.postValue(WorkState.Loading)
            when (val response = logOutUseCase.logOutAccount(sessionId)) {
                is NetworkResult.Success -> {
                    _logOutState.postValue(WorkState.Success(response.value))
                }
                is NetworkResult.Failure -> {
                    _logOutState.postValue(WorkState.Failure(response.message))
                }
            }
        }
    }

}