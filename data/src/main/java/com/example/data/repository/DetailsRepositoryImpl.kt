package com.example.data.repository

import com.example.data.database.dao.CreditsDao
import com.example.data.database.dao.DetailDao
import com.example.data.network.api.DetailsAPI
import com.example.data.util.Connectivity
import com.example.data.util.checkNull
import com.example.data.util.mapToCastDomain
import com.example.data.util.mapToShowDetailsDomain
import com.example.domain.model.Cast
import com.example.domain.model.RequestResult
import com.example.domain.model.ShowDetails
import com.example.domain.repository.DetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val detailsAPI: DetailsAPI,
    private val detailDao: DetailDao,
    private val creditsDao: CreditsDao,
    connectivity: Connectivity,
    scope: CoroutineScope
) :
    DetailsRepository, BaseRepository(connectivity, scope) {

    override fun getDetailsLocal(showId: Int): Flow<RequestResult<ShowDetails?>> {
        return remoteCallWithLocalData(
            apiAction = {
                detailsAPI.getShowDetails(tvId = showId)
            },
            dbAction = {
                detailDao.insert(it.checkNull())
            },
            returnLocalData = {
                detailDao.getDetailsShows(showId)?.mapToShowDetailsDomain()
            }
        )
    }

    override fun getCreditsLocal(showId: Int): Flow<RequestResult<List<Cast>?>> {
        return remoteCallWithLocalData(
            apiAction = {
                detailsAPI.getCredits(tvId = showId)
            },
            dbAction = {
                creditsDao.insert(it)
            },
            returnLocalData = {
                creditsDao.getCreditsShows(showId)?.castItems?.mapToCastDomain()
            }
        )
    }
}
