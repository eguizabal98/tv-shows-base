package com.example.domain.repository

import com.example.domain.model.RequestResult

interface SeasonsRepository<out T> {
    suspend fun fetchSeasons(showsId: Int, seasons: Int): RequestResult<Boolean>
    suspend fun getSeasons(showsId: Int): T
}
