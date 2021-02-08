package com.example.domain.interactors.tvshowsdetail

import com.example.domain.model.ShowDetails

interface GetShowDetailsLocalUseCase {
    suspend fun getShowDetails(showId: Int): ShowDetails
}