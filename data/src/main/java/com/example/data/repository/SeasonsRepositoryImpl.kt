package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.database.dao.SeasonDao
import com.example.data.database.entities.SeasonEntity
import com.example.data.network.api.SeasonsAPI
import com.example.data.util.Connectivity
import com.example.data.util.mapSeasonToRoom
import com.example.data.util.mapSeasonToSeasonDomain
import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import com.example.domain.repository.SeasonsRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SeasonsRepositoryImpl @Inject constructor(
    private val seasonsAPI: SeasonsAPI,
    private val seasonDao: SeasonDao,
    connectivity: Connectivity,
    scope: CoroutineScope
) :
    SeasonsRepository,
    BaseRepository(connectivity, scope) {

    override suspend fun fetchSeasons(showsId: Int, seasons: Int): RequestResult<Boolean> {
        return fetchData(
            apiAction = {
                val seasonsList = arrayListOf<SeasonEntity>()
                for (n in 1..seasons) {
                    seasonsList.add(
                        seasonsAPI.getSeasons(tvId = showsId, seasonNumber = n)
                            .mapSeasonToRoom(showsId)
                    )
                }
                seasonsList
            },
            dbAction = {
                seasonDao.insert(it)
            },
            returnAction = {
                RequestResult.Success(true)
            }
        )
    }

    override suspend fun getSeasons(showsId: Int): LiveData<List<Season>> {
        val value = seasonDao.getSeasons(showsId)
        return Transformations.map(value) { seasonEntityList ->
            seasonEntityList.mapSeasonToSeasonDomain()
        }
    }
}
