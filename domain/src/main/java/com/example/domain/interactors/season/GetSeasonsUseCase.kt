package com.example.domain.interactors.season

import com.example.domain.model.NetworkResult
import com.example.domain.model.Season

interface GetSeasonsUseCase {
    suspend fun getSeasons(showsId: Int, seasons: Int): NetworkResult<List<Season>>
}