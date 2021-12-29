package com.example.domain.interactors.detail

import com.example.domain.model.Cast
import com.example.domain.model.RequestResult
import kotlinx.coroutines.flow.Flow

interface GetCreditsUseCase {
    fun getCredits(showId: Int): Flow<RequestResult<List<Cast>?>>
}
