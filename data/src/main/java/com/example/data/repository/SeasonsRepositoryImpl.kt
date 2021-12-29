package com.example.data.repository

import com.example.data.database.dao.SeasonDao
import com.example.data.database.entities.SeasonEntity
import com.example.data.network.api.SeasonsAPI
import com.example.data.util.Connectivity
import com.example.data.util.mapSeasonToRoom
import com.example.data.util.mapToSeasonDomain
import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import com.example.domain.repository.SeasonsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeasonsRepositoryImpl @Inject constructor(
    private val seasonsAPI: SeasonsAPI,
    private val seasonDao: SeasonDao,
    connectivity: Connectivity,
    scope: CoroutineScope
) :
    SeasonsRepository,
    BaseRepository(connectivity, scope) {

    override fun getSeasons(showsId: Int, seasons: Int): Flow<RequestResult<List<Season>?>> {
        return remoteCallWithLocalData(
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
            returnLocalData = {
                seasonDao.getSeasons(showsId)?.map {
                    it.mapToSeasonDomain()
                }
            }
        )
    }
}
