package com.example.data.repository

import com.example.data.network.api.TvShowSeasonsAPI
import com.example.data.util.mapSeasonToSeasonDomain
import com.example.domain.model.NetworkResult
import com.example.domain.model.Season
import com.example.domain.repository.SeasonsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeasonsRepositoryImpl(private val tvShowSeasonsAPI: TvShowSeasonsAPI) : SeasonsRepository {

    override suspend fun getSeasons(showsId: Int, seasons: Int): NetworkResult<List<Season>> {
        val seasonsList = arrayListOf<Season>()
        withContext(Dispatchers.IO) {
            for (n in 1..seasons) {
                seasonsList.add(
                    tvShowSeasonsAPI.getSeasons(tvId = showsId, seasonNumber = n)
                        .mapSeasonToSeasonDomain()
                )
            }
        }

        return NetworkResult.Success(seasonsList)
    }

}