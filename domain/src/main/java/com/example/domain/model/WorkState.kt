package com.example.domain.model

sealed class WorkState<out T> {
    object Loading : WorkState<Nothing>()
    data class Success<out R>(val value: R) : WorkState<R>()
    data class Failure(
        val error: InternalErrorCodes
    ) : WorkState<Nothing>()
}
