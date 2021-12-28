package com.example.domain.interactors.detail

import androidx.lifecycle.LiveData
import com.example.domain.model.ShowDetails

interface GetDetailsUseCase {
    suspend fun getDetails(showId: Int): LiveData<ShowDetails>
}
