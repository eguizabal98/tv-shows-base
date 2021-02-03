package com.example.tvshowsbase.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.login.GetAuthTokenUseCase
import com.example.domain.interactors.login.GetSessionIdUseCase
import com.example.domain.model.NetworkResult
import com.example.domain.model.WorkState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val getSessionIdUseCase: GetSessionIdUseCase
) : ViewModel() {

    private val _authTokenState = MutableLiveData<WorkState<String>>()
    val authTokeState: LiveData<WorkState<String>> = _authTokenState

    private val _sessionIdState = MutableLiveData<WorkState<String>>()
    val sessionIdState: LiveData<WorkState<String>> = _sessionIdState

    var tokenTemp = ""

    fun getAuthToken(api: String) {
        viewModelScope.launch {
            _authTokenState.postValue(WorkState.Loading)
            when (val response = getAuthTokenUseCase.getAuthToken(api)) {
                is NetworkResult.Success -> {
                    _authTokenState.postValue(WorkState.Success(response.value))
                }
                is NetworkResult.Failure -> {
                    _authTokenState.postValue(WorkState.Failure(response.message))
                }
            }
        }
    }

    fun getSessionId(api: String, token: String) {
        viewModelScope.launch {
            _sessionIdState.postValue(WorkState.Loading)
            when (val response = getSessionIdUseCase.getSessionId(api, token)) {
                is NetworkResult.Success -> {
                    _sessionIdState.postValue(WorkState.Success(response.value))
                }
                is NetworkResult.Failure -> {
                    _sessionIdState.postValue(WorkState.Failure(response.message))
                }
            }
        }
    }

}