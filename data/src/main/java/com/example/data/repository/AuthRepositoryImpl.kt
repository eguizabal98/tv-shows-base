package com.example.data.repository

import com.example.data.database.dao.AccountDao
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.TvShowDao
import com.example.data.network.api.AuthAPI
import com.example.data.network.models.authentication.LogOutBody
import com.example.data.network.models.authentication.SessionRequestBody
import com.example.data.util.Connectivity
import com.example.domain.model.RequestResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val accountDao: AccountDao,
    private val favoriteShowDao: FavoriteShowDao,
    private val tvShowDao: TvShowDao,
    connectivity: Connectivity,
    scope: CoroutineScope
) : AuthRepository, BaseRepository(connectivity, scope) {

    override suspend fun getAuthToken(): RequestResult<String> {
        return fetchDataOnlyNetwork(
            dataProvider = {
                val response = authAPI.getAuthToken()
                RequestResult.Success(response.requestToken)
            }
        )
    }

    override suspend fun getSessionId(token: String): RequestResult<String> {
        return fetchDataOnlyNetwork(
            dataProvider = {
                val response =
                    authAPI.getSessionId(sessionRequestBody = SessionRequestBody(token)).sessionId
                RequestResult.Success(response)
            }
        )
    }

    override suspend fun getAccount(sessionID: String): RequestResult<Int> {
        return fetchData(
            apiAction = {
                authAPI.getAccountId(session = sessionID)
            },
            dbAction = {
                accountDao.insert(it)
            },
            returnAction = {
                RequestResult.Success(accountDao.getAccount().accountId)
            }
        )
    }

    override suspend fun logOutAccount(sessionID: String): RequestResult<Boolean> {
        return fetchData(
            apiAction = {
                authAPI.logOut(logOutBody = LogOutBody(sessionID))
            },
            dbAction = {
                accountDao.clearBase()
                favoriteShowDao.clearBase()
                tvShowDao.clearTvShows()
            },
            returnAction = {
                RequestResult.Success(true)
            }
        )
    }
}
