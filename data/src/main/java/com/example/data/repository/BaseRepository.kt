package com.example.data.repository

import com.example.data.util.Connectivity
import com.example.domain.model.Error
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.RequestResult
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository(
    private val connectivity: Connectivity,
    private val scope: CoroutineScope
) {

    protected suspend fun <T : Any, R : Any, U : Any> fetchData(
        apiAction: suspend () -> T,
        dbAction: suspend (T) -> R,
        returnAction: suspend () -> RequestResult<U>
    ): RequestResult<U> {
        return if (connectivity.hasNetworkAccess()) {
            return withContext(scope.coroutineContext) {
                try {
                    val response = apiAction()
                    dbAction(response)
                    returnAction()
                } catch (e: Exception) {
                    exceptionHandler(e)
                }
            }
        } else {
            withContext(scope.coroutineContext) {
                returnAction()
            }
        }
    }

    protected suspend fun <T : Any> fetchDataOnlyNetwork(dataProvider: suspend () -> RequestResult<T>): RequestResult<T> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(scope.coroutineContext) {
                try {
                    dataProvider()
                } catch (e: HttpException) {
                    exceptionHandler(e)
                }
            }
        } else {
            RequestResult.Failure(Error(null, InternalErrorCodes.NO_INTERNET_ACCESS))
        }
    }

    private fun <T : Any> exceptionHandler(e: Exception): RequestResult<T> {
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    401 -> {
                        RequestResult.Failure(
                            Error(
                                null,
                                InternalErrorCodes.BAD_CREDENTIALS
                            )
                        )
                    }
                    404 -> {
                        RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
                    }
                    else -> {
                        RequestResult.Failure(Error(null, InternalErrorCodes.NOT_SPECIFIC))
                    }
                }
            }
            is IOException -> {
                RequestResult.Failure(Error(null, InternalErrorCodes.NOT_DB_ENTRY))
            }
            else -> RequestResult.Failure(Error(null, InternalErrorCodes.NOT_SPECIFIC))
        }
    }
}
