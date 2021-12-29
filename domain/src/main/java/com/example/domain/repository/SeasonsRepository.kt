package com.example.domain.repository

import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import kotlinx.coroutines.flow.Flow

interface SeasonsRepository {
    fun getSeasons(showsId: Int, seasons: Int): Flow<RequestResult<List<Season>?>>
}
