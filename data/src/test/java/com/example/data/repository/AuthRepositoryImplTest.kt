package com.example.data.repository

import com.example.data.BaseTestData
import com.example.data.database.dao.AccountDao
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.TvShowDao
import com.example.data.mockProfile
import com.example.data.mockSessionId
import com.example.data.mockToken
import com.example.data.mockaccountId
import com.example.data.util.mapToProfileDomain
import com.example.domain.model.Error
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.RequestResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.koin.test.inject
import java.net.HttpURLConnection

class AuthRepositoryImplTest : BaseTestData() {

    private val authRepository: AuthRepository by inject()
    private val accountDao: AccountDao by inject()
    private val favoriteShowDao: FavoriteShowDao by inject()
    private val tvShowDao: TvShowDao by inject()

    // Test getAuthToken
    @Test
    fun `test getAuthToken call API and return success token`() = runBlocking {
        mockNetworkResponseWithFileContent("request_token.json", HttpURLConnection.HTTP_OK)
        val tokenExpected = RequestResult.Success(mockToken)
        val response = authRepository.getAuthToken()

        MatcherAssert.assertThat(response, `is`(tokenExpected))
    }

    @Test
    fun `test getAuthToken call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val response = authRepository.getAuthToken()

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test getAuthToken call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val response = authRepository.getAuthToken()

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }

    // Test getSessionId
    @Test
    fun `test getSessionId call API and return success sessionId`() = runBlocking {
        mockNetworkResponseWithFileContent("session_request.json", HttpURLConnection.HTTP_OK)
        val sessionExpected = RequestResult.Success(mockSessionId)
        val response = authRepository.getSessionId(mockToken)

        MatcherAssert.assertThat(response, `is`(sessionExpected))
    }

    @Test
    fun `test getSessionId call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val response = authRepository.getSessionId(mockToken)

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test getSessionId call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val response = authRepository.getSessionId(mockToken)

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }

    // Test getAccount
    @Test
    fun `test getAccount call API and return success account`() = runBlocking {
        mockNetworkResponseWithFileContent("account_request.json", HttpURLConnection.HTTP_OK)
        val accountIdExpected = RequestResult.Success(mockaccountId)
        val profileExpected = mockProfile

        val responseA = authRepository.getAccount(mockSessionId)
        val responseB = accountDao.getAccount().mapToProfileDomain()

        MatcherAssert.assertThat(responseA, `is`(accountIdExpected))
        MatcherAssert.assertThat(responseB, `is`(profileExpected))
    }

    @Test
    fun `test getAccount call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val response = authRepository.getAccount(mockSessionId)

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test getAccount call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val response = authRepository.getAccount(mockSessionId)

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }

    // Test logOutAccount
    @Test
    fun `test logOutAccount call API and return success logout flow`() = runBlocking {
        mockNetworkResponseWithFileContent("general_response.json", HttpURLConnection.HTTP_OK)
        val responseExpected = RequestResult.Success(true)

        val responseA = authRepository.logOutAccount(mockSessionId)
        val responseB = accountDao.getAccount()
        val responseC = favoriteShowDao.getFavoritesShows().value
        val responseD = tvShowDao.getPopularShows().create().isInvalid

        MatcherAssert.assertThat(responseA, `is`(responseExpected))
        MatcherAssert.assertThat(responseB == null, `is`(true))
        MatcherAssert.assertThat(responseC == null, `is`(true))
        MatcherAssert.assertThat(responseD, `is`(false))
    }

    @Test
    fun `test logOutAccount call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val response = authRepository.logOutAccount(mockSessionId)

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test logOutAccount call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val response = authRepository.logOutAccount(mockSessionId)

        MatcherAssert.assertThat(
            (response as RequestResult.Failure).error.errorCode,
            `is`(responseExpected.error.errorCode)
        )
    }
}
