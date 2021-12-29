package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.Cast
import com.example.domain.model.RequestResult
import com.example.domain.model.ShowDetails
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getDetailsLocal(showId: Int): Flow<RequestResult<ShowDetails?>>
    suspend fun fetchCredits(showId: Int): RequestResult<Boolean>
    suspend fun getCreditsLocal(showId: Int): LiveData<List<Cast>>
}
