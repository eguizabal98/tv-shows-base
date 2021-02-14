package com.example.domain.model

sealed class NetworkResult<out T> {
    data class Success<out R>(val value: R) : NetworkResult<R>()
    data class Failure(
        val message: String,
        val throwable: Throwable?
    ) : NetworkResult<Nothing>()
}
