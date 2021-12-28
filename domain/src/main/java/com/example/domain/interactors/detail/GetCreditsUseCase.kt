package com.example.domain.interactors.detail

import androidx.lifecycle.LiveData
import com.example.domain.model.Cast

interface GetCreditsUseCase {
    suspend fun getCredits(showId: Int): LiveData<List<Cast>>
}
