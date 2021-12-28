package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.BaseTestData
import com.example.data.mockFavorites
import com.example.data.mockSessionId
import com.example.data.mockaccountId
import com.example.domain.model.Error
import com.example.domain.model.InternalErrorCodes
import com.example.domain.model.RequestResult
import com.example.domain.model.TvShow
import com.example.domain.repository.FavoritesRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.koin.core.component.inject
import org.koin.test.inject
import java.net.HttpURLConnection

class FavoritesRepositoryImplTest : BaseTestData() {

    private val favoritesRepository: FavoritesRepository<LiveData<List<TvShow>>> by inject()

    // Test refreshFavoritesShows
    @Test
    fun `test refreshFavoritesShows call API and return success favorites`() = runBlocking {
        mockNetworkResponseWithFileContent("favorites_response.json", HttpURLConnection.HTTP_OK)
        val expectedResponse = RequestResult.Success(true)
        val expectedFavoritesResponse = mockFavorites

        val favoritesResponse =
            favoritesRepository.refreshFavoritesShows(mockaccountId, mockSessionId, 1)
        val favoritesLocalResponse = favoritesRepository.getFavoritesShows().getOrAwaitValue()

        MatcherAssert.assertThat(favoritesResponse, Matchers.`is`(expectedResponse))
        MatcherAssert.assertThat(expectedFavoritesResponse, Matchers.`is`(favoritesLocalResponse))
    }

    @Test
    fun `test refreshFavoritesShows call API and return fail 404`() = runBlocking {
        mockNetworkResponseWithFileContent("404_response.json", HttpURLConnection.HTTP_NOT_FOUND)
        val responseExpected = RequestResult.Failure(Error(null, InternalErrorCodes.NOT_FOUND))
        val favoritesResponse =
            favoritesRepository.refreshFavoritesShows(mockaccountId, mockSessionId, 1)

        MatcherAssert.assertThat(
            (favoritesResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }

    @Test
    fun `test refreshFavoritesShows call API and return fail 401`() = runBlocking {
        mockNetworkResponseWithFileContent("401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)
        val responseExpected =
            RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
        val favoritesResponse =
            favoritesRepository.refreshFavoritesShows(mockaccountId, mockSessionId, 1)

        MatcherAssert.assertThat(
            (favoritesResponse as RequestResult.Failure).error.errorCode,
            Matchers.`is`(responseExpected.error.errorCode)
        )
    }
}
