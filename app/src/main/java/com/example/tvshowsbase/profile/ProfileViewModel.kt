package com.example.tvshowsbase.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.account.GetAccountUseCase
import com.example.domain.interactors.tvshowsfavorite.GetFavoriteShowsUseCase
import com.example.domain.model.NetworkResult
import com.example.domain.model.Profile
import com.example.domain.model.TvShow
import com.example.domain.model.WorkState
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val getFavoriteShowsUseCase: GetFavoriteShowsUseCase
) : ViewModel() {

    private val _account = MutableLiveData<WorkState<Profile>>()
    val account: LiveData<WorkState<Profile>>
        get() = _account

    private val _favorites = MutableLiveData<List<TvShow>>()
    val favorites: LiveData<List<TvShow>>
        get() = _favorites

    init {
        getFavorites()
    }

    fun getAccount(sessionId: String) {
        viewModelScope.launch {
            _account.postValue(WorkState.Loading)
            when (val response = getAccountUseCase.getAccount(sessionId)) {
                is NetworkResult.Success -> {
                    Log.d("Account", response.value.toString())
                    _account.postValue(WorkState.Success(response.value))
                }
                is NetworkResult.Failure -> {
                    Log.e("Account", response.message)
                    _account.postValue(WorkState.Failure(response.message))
                }
            }
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            _favorites.postValue(getFavoriteShowsUseCase.getFavoriteShows())
        }
    }

}