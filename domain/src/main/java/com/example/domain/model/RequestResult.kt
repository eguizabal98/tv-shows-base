package com.example.domain.model

sealed class RequestResult<out T> {
    data class Success<out R>(val value: R) : RequestResult<R>()
    data class Failure(val error: Error) : RequestResult<Nothing>()
}

class Error(val throwable: Throwable?, val errorCode: InternalErrorCodes)

enum class InternalErrorCodes {
    NO_INTERNET_ACCESS,
    BAD_CREDENTIALS,
    NOT_FOUND,
    NOT_SPECIFIC,
    NOT_DB_ENTRY
}
