package com.example.data.repository

import com.example.data.database.dao.AccountDao
import com.example.data.util.mapToProfileDomain
import com.example.domain.model.NetworkResult
import com.example.domain.model.Profile
import com.example.domain.repository.AccountRepository

class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {

    override suspend fun getAccountDetails(sessionId: String): NetworkResult<Profile> {
        return try {
            val response = accountDao.getAccount()

            NetworkResult.Success(response.mapToProfileDomain())
        } catch (e: Exception) {
            NetworkResult.Failure(e.message.toString(), e.cause)
        }
    }

}