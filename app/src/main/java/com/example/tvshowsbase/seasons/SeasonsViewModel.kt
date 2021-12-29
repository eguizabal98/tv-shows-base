package com.example.tvshowsbase.seasons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.season.GetSeasonUseCase
import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import com.example.domain.model.WorkState
import com.example.tvshowsbase.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonsViewModel @Inject constructor(
    private val getSeasonUseCase: GetSeasonUseCase
) : BaseViewModel() {

    private val _seasonRequest = MutableLiveData<WorkState<Boolean>>()
    val seasonRequest: LiveData<WorkState<Boolean>>
        get() = _seasonRequest

    private val _seasonList = MutableLiveData<List<Season>>()
    val seasonList: LiveData<List<Season>> = _seasonList

    fun getSeasons(showId: Int, seasons: Int) {
        viewModelScope.launch {
            getSeasonUseCase.getSeasons(showId, seasons).collect { requestResult ->
                when (requestResult) {
                    RequestResult.Loading -> addWork()
                    is RequestResult.Success -> {
                        requestResult.value?.let { seasonList ->
                            _seasonList.postValue(seasonList)
                        }
                    }
                    is RequestResult.Failure -> removeWork()
                }
            }
        }
    }
}
