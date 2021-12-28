package com.example.domain.interactors.season

import androidx.lifecycle.LiveData
import com.example.domain.model.Season

interface GetSeasonUseCase {
    suspend fun getSeasons(showsId: Int): LiveData<List<Season>>
}
