package com.example.domain.repository

import com.example.domain.model.Cast
import com.example.domain.model.RequestResult
import com.example.domain.model.ShowDetails
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getDetailsLocal(showId: Int): Flow<RequestResult<ShowDetails?>>
    fun getCreditsLocal(showId: Int): Flow<RequestResult<List<Cast>?>>
}
