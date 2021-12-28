package com.example.tvshowsbase.showslist

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.domain.interactors.account.LogOutUseCase
import com.example.domain.interactors.favorite.RefreshFavoritesUseCase
import com.example.domain.interactors.showlist.ChangeFilterUseCase
import com.example.domain.interactors.showlist.GetTvShowsUseCase
import com.example.domain.interactors.showlist.SetInitialFilter
import com.example.domain.model.FilterType
import com.example.domain.model.RequestResult
import com.example.domain.model.TvShow
import com.example.domain.model.WorkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    getTvShowsUseCase: GetTvShowsUseCase,
    private val refreshFavoritesUseCase: RefreshFavoritesUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val changeFilterUseCase: ChangeFilterUseCase,
    private val setInitialFilter: SetInitialFilter
) :
    ViewModel() {

    private var firstCall = true

    private val _logOutState = MutableLiveData<WorkState<Boolean>>()
    val logOutState: LiveData<WorkState<Boolean>>
        get() = _logOutState

    val showsList: LiveData<PagedList<TvShow>> =
        getTvShowsUseCase.getShows()

    fun getFavorites(accountId: Int, sessionId: String, page: Int) {
        if (firstCall) {
            viewModelScope.launch {
                refreshFavoritesUseCase.refreshFavoritesShows(accountId, sessionId, page)
            }
            firstCall = false
        }
    }

    fun changeFilter(filterType: FilterType) {
        changeFilterUseCase.changeFilter(filterType)
    }

    fun setInitialFilter(filterType: FilterType) {
        setInitialFilter.setInitialFilter(filterType)
    }

    fun logOut(sessionId: String) {
        viewModelScope.launch {
            _logOutState.postValue(WorkState.Loading)
            when (val response = logOutUseCase.logOutAccount(sessionId)) {
                is RequestResult.Success -> {
                    _logOutState.postValue(WorkState.Success(response.value))
                }
                is RequestResult.Failure -> {
                    _logOutState.postValue(WorkState.Failure(response.error.errorCode))
                }
            }
        }
    }
}
