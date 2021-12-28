package com.example.data.repository

import com.example.data.database.dao.AccountDao
import com.example.data.network.api.AuthAPI
import com.example.data.util.Connectivity
import com.example.data.util.mapToProfileDomain
import com.example.domain.model.Profile
import com.example.domain.model.RequestResult
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao, private val authAPI: AuthAPI, connectivity: Connectivity, scope: CoroutineScope) :
    AccountRepository,
    BaseRepository(connectivity, scope) {

    override suspend fun getAccountDetails(sessionId: String): RequestResult<Profile> {
        val response = accountDao.getAccount()
        return RequestResult.Success(response.mapToProfileDomain())
    }

    override suspend fun fetchAccount(sessionID: String): RequestResult<Profile> {
        return fetchData(
            apiAction = {
                authAPI.getAccountId(session = sessionID)
            },
            dbAction = {
                accountDao.insert(it)
            },
            returnAction = {
                RequestResult.Success(accountDao.getAccount().mapToProfileDomain())
            }
        )
    }
}
