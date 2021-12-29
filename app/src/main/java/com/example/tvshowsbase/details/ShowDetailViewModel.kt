package com.example.tvshowsbase.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.detail.FetchCastListUseCase
import com.example.domain.interactors.detail.GetCreditsUseCase
import com.example.domain.interactors.detail.GetDetailsUseCase
import com.example.domain.interactors.favorite.GetFavoritesUseCase
import com.example.domain.interactors.favorite.PutFavoriteUseCase
import com.example.domain.model.Cast
import com.example.domain.model.RequestResult
import com.example.domain.model.ShowDetails
import com.example.domain.model.TvShow
import com.example.domain.model.WorkState
import com.example.tvshowsbase.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val fetchCastListUseCase: FetchCastListUseCase,
    private val putFavoriteUseCase: PutFavoriteUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getDetailsUseCase: GetDetailsUseCase
) : BaseViewModel() {

    private val _favoriteRequest = MutableLiveData<WorkState<Boolean>>()
    val favoriteRequest: LiveData<WorkState<Boolean>>
        get() = _favoriteRequest

    lateinit var favoriteResult: LiveData<List<TvShow>>

    var favoriteState = false

    private val _castRequest = MutableLiveData<WorkState<Boolean>>()
    val castRequest: LiveData<WorkState<Boolean>>
        get() = _castRequest

    private val _showDetails = MutableLiveData<ShowDetails>()
    val showDetails: LiveData<ShowDetails> = _showDetails

    lateinit var cast: LiveData<List<Cast>>

    init {
        checkFavorite()
    }

    fun getDetails(showId: Int) {
        viewModelScope.launch {
            getDetailsUseCase.getDetails(showId).collect { requestResult ->
                when (requestResult) {
                    RequestResult.Loading -> addWork()
                    is RequestResult.Success -> {
                        removeWork()
                        requestResult.value?.let {
                            _showDetails.postValue(it)
                        }
                    }
                    is RequestResult.Failure -> removeWork()
                }
            }
        }
    }

    fun fetchCast(showId: Int) {
        viewModelScope.launch {
            _castRequest.postValue(WorkState.Loading)
            when (val result = fetchCastListUseCase.fetchCast(showId)) {
                is RequestResult.Success -> {
                    _castRequest.postValue(WorkState.Success(result.value))
                }
                is RequestResult.Failure -> {
                    _castRequest.postValue(WorkState.Failure(result.error.errorCode))
                }
            }
        }
    }

    fun getCast(showId: Int) {
        viewModelScope.launch {
            cast = getCreditsUseCase.getCredits(showId)
        }
    }

    fun putFavorite(sessionId: String, accountId: Int) {
        viewModelScope.launch {
            _showDetails.value?.let { showDetails ->
                _favoriteRequest.postValue(WorkState.Loading)
                val response = putFavoriteUseCase.putShowFavorite(
                    !favoriteState,
                    showDetails.showId,
                    sessionId,
                    accountId
                )
                when (response) {
                    is RequestResult.Failure -> {
                        _favoriteRequest.postValue(WorkState.Failure(response.error.errorCode))
                    }
                    is RequestResult.Success -> {
                        _favoriteRequest.postValue(WorkState.Success(response.value))
                    }
                }
            }
        }
    }

    private fun checkFavorite() {
        viewModelScope.launch {
            favoriteResult = getFavoritesUseCase.getFavoriteShows()
        }
    }
}
