package com.example.tvshowsbase.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.tvshowsdetail.GetCastListUseCase
import com.example.domain.interactors.tvshowsdetail.GetShowDetailsLocalUseCase
import com.example.domain.interactors.tvshowsfavorite.GetFavoriteShowsUseCase
import com.example.domain.interactors.tvshowsfavorite.PutShowFavoriteUseCase
import com.example.domain.model.Cast
import com.example.domain.model.NetworkResult
import com.example.domain.model.ShowDetails
import kotlinx.coroutines.launch

class ShowDetailViewModel(
    private val getShowDetailsLocalUseCase: GetShowDetailsLocalUseCase,
    private val getCastListUseCase: GetCastListUseCase,
    private val putShowFavoriteUseCase: PutShowFavoriteUseCase,
    private val getFavoriteShowsUseCase: GetFavoriteShowsUseCase
) :
    ViewModel() {

    private val _favoriteResult = MutableLiveData<NetworkResult<Boolean>>()
    val favoriteResult: LiveData<NetworkResult<Boolean>>
        get() = _favoriteResult

    var favoriteState = false

    private val _details = MutableLiveData<ShowDetails>()
    val details: LiveData<ShowDetails>
        get() = _details

    private val _cast = MutableLiveData<List<Cast>>()
    val cast: LiveData<List<Cast>>
        get() = _cast

    fun getDetails(showId: Int) {
        viewModelScope.launch {
            val response = getShowDetailsLocalUseCase.getShowDetails(showId)
            _details.postValue(response)
            Log.d("DetailsViewModel", "$response")
            checkFavorite(response.showId)
        }

    }

    fun getCast(showId: Int) {
        viewModelScope.launch {
            _cast.postValue(getCastListUseCase.getCast(showId))
        }
    }

    fun putFavorite(sessionId: String, accountId: Int) {
        viewModelScope.launch {
            _details.value?.let { showDetails ->
                val response = putShowFavoriteUseCase.putShowFavorite(
                    !favoriteState,
                    showDetails.showId,
                    sessionId,
                    accountId
                )
                _favoriteResult.postValue(response)
            }
        }
    }

    private fun checkFavorite(showId: Int) {
        viewModelScope.launch {
            val favoriteList = getFavoriteShowsUseCase.getFavoriteShows()
            favoriteList.forEach {
                if (it.showId == showId) {
                    _favoriteResult.postValue(NetworkResult.Success(true))
                }
            }
        }
    }

}