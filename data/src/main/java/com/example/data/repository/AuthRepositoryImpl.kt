package com.example.data.repository

import android.util.Log
import com.example.data.database.dao.AccountDao
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.TvShowDao
import com.example.data.network.api.AuthAPI
import com.example.data.network.models.authentication.LogOutBody
import com.example.data.network.models.authentication.SessionRequestBody
import com.example.data.util.Connectivity
import com.example.domain.model.NetworkResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class AuthRepositoryImpl(
    private val authAPI: AuthAPI,
    private val connectivity: Connectivity,
    private val accountDao: AccountDao,
    private val favoriteShowDao: FavoriteShowDao,
    private val tvShowDao: TvShowDao
) : AuthRepository {

    override suspend fun getAuthToken(): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                if (connectivity.hasNetworkAccess()) {
                    val response = authAPI.getAuthToken()
                    NetworkResult.Success(response.requestToken)
                } else {
                    NetworkResult.Failure(noNetworkAccess, Throwable())
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                    404 -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                    else -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                }
            }
        }
    }

    override suspend fun getSessionId(token: String): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                if (connectivity.hasNetworkAccess()) {
                    val response =
                        authAPI.getSessionId(sessionRequestBody = SessionRequestBody(token)).sessionId
                    NetworkResult.Success(response)
                } else {
                    NetworkResult.Failure(noNetworkAccess, Throwable())
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                    404 -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                    else -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                }
            }
        }
    }

    override suspend fun getAccountId(sessionID: String): NetworkResult<Int> {
        return withContext(Dispatchers.IO) {
            try {
                if (connectivity.hasNetworkAccess()) {
                    val response = authAPI.getAccountId(session = sessionID)
                    accountDao.insert(response)
                    NetworkResult.Success(response.accountId)
                } else {
                    NetworkResult.Failure(noNetworkAccess, Throwable())
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                    404 -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                    else -> {
                        NetworkResult.Failure(networkError, e.cause)
                    }
                }
            }
        }
    }

    override suspend fun logOutAccount(sessionID: String): NetworkResult<Boolean> {
        return try {
            Log.d("LogOut", sessionID)
            val response = authAPI.logOut(logOutBody = LogOutBody(sessionID))
            Log.d("LogOut", "$response")
            if (response.success == true) {
                accountDao.clearBase()
                favoriteShowDao.clearBase()
                tvShowDao.clearTvShows()
                NetworkResult.Success(true)
            } else {
                NetworkResult.Success(false)
            }

        } catch (e: Exception) {
            NetworkResult.Failure(e.message.toString(), e.cause)
        }
    }

    companion object {
        const val networkError = "Something is wrong, try again"
        const val noNetworkAccess = "No internet access"
    }

}
