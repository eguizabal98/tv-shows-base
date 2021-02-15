package com.example.domain.interactors.detail

import com.example.domain.model.RequestResult

interface FetchDetailsUseCase {
    suspend fun getShowDetails(showId: Int): RequestResult<Boolean>
}