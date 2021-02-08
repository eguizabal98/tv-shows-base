package com.example.tvshowsbase.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.login.GetAccountIdUseCase
import com.example.domain.interactors.login.GetAuthTokenUseCase
import com.example.domain.interactors.login.GetSessionIdUseCase
import com.example.domain.model.NetworkResult
import com.example.domain.model.WorkState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val getSessionIdUseCase: GetSessionIdUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase
) : ViewModel() {

    private val _authTokenState = MutableLiveData<WorkState<String>>()
    val authTokeState: LiveData<WorkState<String>> = _authTokenState

    private val _sessionIdState = MutableLiveData<WorkState<String>>()
    val sessionIdState: LiveData<WorkState<String>> = _sessionIdState

    private val _accountIdState = MutableLiveData<WorkState<Int>>()
    val accountIdState: LiveData<WorkState<Int>> = _accountIdState

    var tokenTemp = ""

    fun getAuthToken() {
        _authTokenState.value = WorkState.Loading
        viewModelScope.launch {
            when (val response = getAuthTokenUseCase.getAuthToken()) {
                is NetworkResult.Success -> {
                    _authTokenState.postValue(WorkState.Success(response.value))
                }
                is NetworkResult.Failure -> {
                    _authTokenState.postValue(WorkState.Failure(response.message))
                }
            }
        }
    }

    fun getSessionId(token: String) {
        _sessionIdState.value = WorkState.Loading
        viewModelScope.launch {
            when (val response = getSessionIdUseCase.getSessionId(token)) {
                is NetworkResult.Success -> {
                    _sessionIdState.postValue(WorkState.Success(response.value))
                }
                is NetworkResult.Failure -> {
                    _sessionIdState.postValue(WorkState.Failure(response.message))
                }
            }
        }
    }

    fun getAccountId(sessionId: String) {
        viewModelScope.launch {
            when (val response = getAccountIdUseCase.getAccountId(sessionId)) {
                is NetworkResult.Success -> {
                    _accountIdState.postValue(WorkState.Success(response.value))
                }
                is NetworkResult.Failure -> {
                    _accountIdState.postValue(WorkState.Failure(response.message))
                }
            }
        }
    }

}