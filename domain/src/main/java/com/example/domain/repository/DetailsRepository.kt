package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.Cast
import com.example.domain.model.RequestResult
import com.example.domain.model.ShowDetails

interface DetailsRepository {
    suspend fun fetchDetails(showId: Int): RequestResult<Boolean>
    suspend fun getDetailsLocal(showId: Int): LiveData<ShowDetails>
    suspend fun fetchCredits(showId: Int): RequestResult<Boolean>
    suspend fun getCreditsLocal(showId: Int): LiveData<List<Cast>>
}
