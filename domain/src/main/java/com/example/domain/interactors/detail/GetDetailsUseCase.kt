package com.example.domain.interactors.detail

import com.example.domain.model.RequestResult
import com.example.domain.model.ShowDetails
import kotlinx.coroutines.flow.Flow

interface GetDetailsUseCase {
    fun getDetails(showId: Int): Flow<RequestResult<ShowDetails?>>
}
