package com.example.data.repository

import com.example.data.util.Connectivity
import com.example.domain.model.Error
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.RequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

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

    protected fun <T, R, U> remoteCallWithLocalData(
        apiAction: suspend () -> T,
        dbAction: suspend (T) -> R,
        returnLocalData: suspend () -> U
    ): Flow<RequestResult<U>> {
        return flow {
            emit(RequestResult.Loading)
            if (connectivity.hasNetworkAccess()) {
                val response = apiAction()
                dbAction(response)
                emit(RequestResult.Success(returnLocalData()))
            } else {
                emit(RequestResult.Success(returnLocalData()))
            }
        }.catch { e ->
            emit(exceptionHandler(Exception(e)))
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
