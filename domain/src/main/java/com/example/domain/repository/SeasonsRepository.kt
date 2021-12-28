package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.RequestResult
import com.example.domain.model.Season

interface SeasonsRepository {
    suspend fun fetchSeasons(showsId: Int, seasons: Int): RequestResult<Boolean>
    suspend fun getSeasons(showsId: Int): LiveData<List<Season>>
}
