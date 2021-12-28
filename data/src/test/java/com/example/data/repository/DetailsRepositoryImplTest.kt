package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.BaseTestData
import com.example.data.mockCast
import com.example.data.mockDetail
import com.example.data.mockShowId
import com.example.domain.model.Cast
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.RequestResult
import com.example.domain.model.ShowDetails
import com.example.domain.repository.DetailsRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.koin.core.component.inject
import org.koin.test.inject
import java.net.HttpURLConnection

class DetailsRepositoryImplTest : BaseTestData() {

    private val detailsRepository: DetailsRepository<LiveData<List<Cast>>, LiveData<ShowDetails>> by inject()

    // Test fetchDetails
    @Test
    fun `test fetchDetails call API and return success details`() = runBlocking {
        mockNetworkResponseWithFileContent("detail_wandavision.json", HttpURLConnection.HTTP_OK)
        val expectedResponse = RequestResult.Success(true)
        val detailExpectedResponse = mockDetail

        val detailsResponse = detailsRepository.fetchDetails(mockShowId)
        val detailsLocalResult = detailsRepository.getDetailsLocal(mockShowId).getOrAwaitValue()

        MatcherAssert.assertThat(detailsResponse, Matchers.`is`(expectedResponse))
        MatcherAssert.assertThat(detailsLocalResult, Matchers.`is`(detailExpectedResponse))
    }

    @Test
    fun `test fetchDetails call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val detailsResponse = detailsRepository.fetchDetails(mockShowId)

        MatcherAssert.assertThat(
            (detailsResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test fetchDetails call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val detailsResponse = detailsRepository.fetchDetails(mockShowId)

        MatcherAssert.assertThat(
            (detailsResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }

    // Test fetchCredits
    @Test
    fun `test fetchCredits call API and return success credits`() = runBlocking {
        mockNetworkResponseWithFileContent("credits_wandavision.json", HttpURLConnection.HTTP_OK)
        val expectedResponse = RequestResult.Success(true)
        val creditsExpectedResponse = mockCast

        val creditsResponse = detailsRepository.fetchCredits(mockShowId)
        val creditsLocalResult = detailsRepository.getCreditsLocal(mockShowId).getOrAwaitValue()

        MatcherAssert.assertThat(creditsResponse, Matchers.`is`(expectedResponse))
        MatcherAssert.assertThat(creditsLocalResult, Matchers.`is`(creditsExpectedResponse))
    }

    @Test
    fun `test fetchCredits call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val creditsResponse = detailsRepository.fetchCredits(mockShowId)

        MatcherAssert.assertThat(
            (creditsResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test fetchCredits call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val creditsResponse = detailsRepository.fetchCredits(mockShowId)

        MatcherAssert.assertThat(
            (creditsResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }
}
