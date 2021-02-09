package com.example.domain.repository

import com.example.domain.model.NetworkResult
import com.example.domain.model.Season

interface SeasonsRepository {
    suspend fun getSeasons(showsId: Int, seasons: Int): NetworkResult<List<Season>>
}