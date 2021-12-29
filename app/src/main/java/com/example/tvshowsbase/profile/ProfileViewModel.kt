package com.example.tvshowsbase.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.account.FetchAccountUseCase
import com.example.domain.interactors.account.GetAccountUseCase
import com.example.domain.interactors.favorite.GetFavoritesUseCase
import com.example.domain.model.Profile
import com.example.domain.model.RequestResult
import com.example.domain.model.TvShow
import com.example.domain.model.WorkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val fetchAccountUseCase: FetchAccountUseCase
) : ViewModel() {

    private val _account = MutableLiveData<WorkState<Profile>>()
    val account: LiveData<WorkState<Profile>>
        get() = _account

    lateinit var favoriteResult: LiveData<List<TvShow>>

    init {
        getFavorites()
    }

    fun getAccount(sessionId: String) {
        viewModelScope.launch {
            when (val response = getAccountUseCase.getAccount(sessionId)) {
                is RequestResult.Success -> {
                    _account.postValue(WorkState.Success(response.value))
                }
                is RequestResult.Failure -> {
                    _account.postValue(WorkState.Failure(response.error.errorCode))
                }
            }
            fetchAccount(sessionId)
        }
    }

    private fun fetchAccount(sessionId: String) {
        viewModelScope.launch {
            _account.postValue(WorkState.Loading)
            when (val response = fetchAccountUseCase.fetchAccount(sessionId)) {
                is RequestResult.Success -> {
                    _account.postValue(WorkState.Success(response.value))
                }
                is RequestResult.Failure -> {
                    _account.postValue(WorkState.Failure(response.error.errorCode))
                }
            }
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            favoriteResult = getFavoritesUseCase.getFavoriteShows().asLiveData()
        }
    }
}
