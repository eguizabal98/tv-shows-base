package com.example.domain.repository

import com.example.domain.model.Cast
import com.example.domain.model.ShowDetails

interface ShowDetailsRepository {
    suspend fun getShowDetailsLocal(showId: Int): ShowDetails
    suspend fun getCredits(showId: Int): List<Cast>

}