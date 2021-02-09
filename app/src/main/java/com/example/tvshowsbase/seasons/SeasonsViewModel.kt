package com.example.tvshowsbase.seasons

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.season.GetSeasonsUseCase
import com.example.domain.model.NetworkResult
import com.example.domain.model.Season
import com.example.domain.model.WorkState
import kotlinx.coroutines.launch

class SeasonsViewModel(private val getSeasonsUseCase: GetSeasonsUseCase) : ViewModel() {

    private val _seasonList = MutableLiveData<WorkState<List<Season>>>()
    val seasonList: LiveData<WorkState<List<Season>>>
        get() = _seasonList

    fun getSeasons(showsId: Int, seasons: Int) {
        _seasonList.value = WorkState.Loading
        viewModelScope.launch {
            when (val response = getSeasonsUseCase.getSeasons(showsId, seasons)) {
                is NetworkResult.Failure -> {
                    Log.d("Seasons", response.message)
                    _seasonList.postValue(WorkState.Failure(response.message))
                }
                is NetworkResult.Success -> {
                    Log.d("Seasons", response.value.toString())
                    _seasonList.postValue(WorkState.Success(response.value))
                }
            }
        }
    }
}