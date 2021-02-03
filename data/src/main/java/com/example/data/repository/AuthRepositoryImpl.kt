package com.example.data.repository

import com.example.data.network.NetworkAPI
import com.example.data.network.networkError
import com.example.data.network.noNetworkAccess
import com.example.data.network.requestModels.SessionRequestBody
import com.example.data.util.Connectivity
import com.example.domain.model.NetworkResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val networkAPI: NetworkAPI,
    private val connectivity: Connectivity
) : AuthRepository {

    override suspend fun getAuthToken(apiKey: String): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                if (connectivity.hasNetworkAccess()) {
                    val response = networkAPI.getAuthToken(apiKey)
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

    override suspend fun getSessionId(apiKey: String, token: String): NetworkResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                if (connectivity.hasNetworkAccess()) {
                    val response =
                        networkAPI.getSessionId(apiKey, SessionRequestBody(token)).sessionId
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

}
