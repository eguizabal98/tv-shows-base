package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.BaseTestData
import com.example.data.mockSeason
import com.example.data.mockShowId
import com.example.domain.model.Error
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.RequestResult
import com.example.domain.model.Season
import com.example.domain.repository.SeasonsRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.koin.core.component.inject
import org.koin.test.inject
import java.net.HttpURLConnection

class SeasonsRepositoryImplTest : BaseTestData() {

    private val seasonsRepository: SeasonsRepository<LiveData<List<Season>>> by inject()

    // Test fetchSeasons
    @Test
    fun `test fetchSeasons call API and return success seasons`() = runBlocking {
        mockNetworkResponseWithFileContent("season_wanda_1.json", HttpURLConnection.HTTP_OK)
        val expectedResponse = RequestResult.Success(true)
        val seasonsExpectedResponse = mockSeason

        val seasonsResponse = seasonsRepository.fetchSeasons(mockShowId, 1)
        val seasonsLocalResult = seasonsRepository.getSeasons(mockShowId).getOrAwaitValue()

        MatcherAssert.assertThat(seasonsResponse, Matchers.`is`(expectedResponse))
        MatcherAssert.assertThat(seasonsLocalResult, Matchers.`is`(seasonsExpectedResponse))
    }

    @Test
    fun `test fetchSeasons call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val seasonsResponse = seasonsRepository.fetchSeasons(mockShowId, 1)

        MatcherAssert.assertThat(
            (seasonsResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test fetchSeasons call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val seasonsResponse = seasonsRepository.fetchSeasons(mockShowId, 1)

        MatcherAssert.assertThat(
            (seasonsResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }
}
