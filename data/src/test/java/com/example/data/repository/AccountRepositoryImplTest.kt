package com.example.data.repository

import com.example.data.BaseTestData
import com.example.data.mockProfile
import com.example.domain.model.Error
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.RequestResult
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import java.net.HttpURLConnection

class AccountRepositoryImplTest : BaseTestData(), KoinTest {

    private val accountRepository: AccountRepository by inject()

    @Test
    fun `test fetchAccount call API and return success profile domain`() = runBlocking {
        mockNetworkResponseWithFileContent("account_request.json", HttpURLConnection.HTTP_OK)
        val profileExpected = RequestResult.Success(mockProfile)

        val responseA = accountRepository.fetchAccount(sessionId)
        val responseB = accountRepository.getAccountDetails(sessionId)

        MatcherAssert.assertThat(responseA, `is`(profileExpected))
        MatcherAssert.assertThat(responseB, `is`(profileExpected))
    }

    @Test
    fun `test fetchAccount call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val profileExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val responseA = accountRepository.fetchAccount(sessionId)

        MatcherAssert.assertThat((responseA as RequestResult.Failure).error.errorCode, `is`(profileExpected.error.errorCode))
    }

    @Test
    fun `test fetchAccount call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val profileExpected = RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val responseA = accountRepository.fetchAccount(sessionId)

        MatcherAssert.assertThat((responseA as RequestResult.Failure).error.errorCode, `is`(profileExpected.error.errorCode))
    }
}
