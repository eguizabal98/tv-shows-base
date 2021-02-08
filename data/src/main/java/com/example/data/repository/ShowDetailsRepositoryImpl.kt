package com.example.data.repository

import android.util.Log
import com.example.data.network.api.TvShowDetailsAPI
import com.example.data.util.mapToCastDomain
import com.example.data.util.mapToShowDetailsDomain
import com.example.domain.model.Cast
import com.example.domain.model.ShowDetails
import com.example.domain.repository.ShowDetailsRepository

class ShowDetailsRepositoryImpl(
    private val tvShowDetailsAPI: TvShowDetailsAPI
) :
    ShowDetailsRepository {

    override suspend fun getShowDetailsLocal(showId: Int): ShowDetails {
        val response = tvShowDetailsAPI.getShowDetails(tvId = showId)
        return response.mapToShowDetailsDomain()
    }

    override suspend fun getCredits(showId: Int): List<Cast> {
        val value = tvShowDetailsAPI.getCredits(tvId = showId)
        Log.d("ShowDetailsRepository", value.castItems.toString())
        return value.castItems.mapToCastDomain()
    }
}