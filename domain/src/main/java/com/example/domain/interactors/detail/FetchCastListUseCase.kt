package com.example.domain.interactors.detail

import com.example.domain.model.RequestResult

interface FetchCastListUseCase {
    suspend fun fetchCast(showId: Int): RequestResult<Boolean>
}