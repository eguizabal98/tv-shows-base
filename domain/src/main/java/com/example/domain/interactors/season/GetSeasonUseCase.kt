package com.example.domain.interactors.season

import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import kotlinx.coroutines.flow.Flow

interface GetSeasonUseCase {
    fun getSeasons(showsId: Int, seasons: Int): Flow<RequestResult<List<Season>?>>
}
