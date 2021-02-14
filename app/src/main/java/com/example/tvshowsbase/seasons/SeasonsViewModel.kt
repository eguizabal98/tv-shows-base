package com.example.tvshowsbase.seasons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.season.FetchSeasonsUseCase
import com.example.domain.interactors.season.GetSeasonUseCase
import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import com.example.domain.model.WorkState
import kotlinx.coroutines.launch

class SeasonsViewModel(
    private val fetchSeasonsUseCase: FetchSeasonsUseCase,
    private val getSeasonUseCase: GetSeasonUseCase<LiveData<List<Season>>>
) : ViewModel() {

    private val _seasonRequest = MutableLiveData<WorkState<Boolean>>()
    val seasonRequest: LiveData<WorkState<Boolean>>
        get() = _seasonRequest

    lateinit var seasonList: LiveData<List<Season>>

    fun fetchSeasons(showsId: Int, seasons: Int) {
        viewModelScope.launch {
            _seasonRequest.postValue(WorkState.Loading)
            when (val response = fetchSeasonsUseCase.getSeasons(showsId, seasons)) {
                is RequestResult.Success -> {
                    _seasonRequest.postValue(WorkState.Success(response.value))
                }
                is RequestResult.Failure -> {
                    _seasonRequest.postValue(WorkState.Failure(response.error.errorCode))
                }
            }
        }
    }

    fun getSeasons(showId: Int) {
        viewModelScope.launch {
            seasonList = getSeasonUseCase.getSeasons(showId)
        }
    }

}