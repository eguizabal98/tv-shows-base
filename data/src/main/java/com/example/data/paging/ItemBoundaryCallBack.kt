package com.example.data.paging

import androidx.paging.PagedList
import androidx.room.withTransaction
import com.example.data.database.DataBase
import com.example.data.database.entities.TVShowEntity
import com.example.data.network.api.TvShowsAPI
import com.example.data.network.models.showslist.TvShowsResult
import com.example.domain.model.*
import com.example.domain.model.FilterType.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val INITIAL_PAGE = 1
const val NEXT_PAGE_RANGE = 1
const val INITIAL_PAGES = 3

class ItemBoundaryCallBack(
    private val tvShowsAPI: TvShowsAPI,
    private val dataBase: DataBase,
    private val scope: CoroutineScope
) :
    PagedList.BoundaryCallback<TvShow>() {

    private var actualFilter: FilterType = POPULAR
    private var lastFilter: FilterType = POPULAR

    fun setInitialFilter(filterType: FilterType) {
        actualFilter = filterType
        lastFilter = filterType
    }

    fun onChange(filterType: FilterType) {
        actualFilter = filterType
        scope.launch {
            val actualItems = dataBase.tvShowDao().getDataCount()
            if (actualFilter != lastFilter || actualItems == 0) {
                lastFilter = actualFilter

                actualFilter = filterType
                dataBase.withTransaction {
                    dataBase.tvShowDao().clearTvShows()
                    dataBase.tvShowDao().getDataCount()
                }
                onZeroItemsLoaded()
            }
        }
    }

    override fun onZeroItemsLoaded() {
        scope.launch {
            try {
                if (dataBase.tvShowDao().getDataCount() == 0) {
                    for (n in INITIAL_PAGE..INITIAL_PAGES) {
                        val response = networkCall(n)
                        val responseMapped = response.items.map {
                            TVShowEntity(
                                it.tvShowId,
                                it.name,
                                it.score,
                                it.airDate,
                                it.posterImage,
                                it.backDropImage,
                                it.description,
                                response.page
                            )
                        }
                        dataBase.tvShowDao().insert(responseMapped)
                    }
                }
            } catch (e: Exception) {
                exceptionHandler(e)
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: TvShow) {
        scope.launch {
            val page = itemAtEnd.page + NEXT_PAGE_RANGE
            try {
                val response = networkCall(page)
                val responseMapped = response.items.map {
                    TVShowEntity(
                        it.tvShowId,
                        it.name,
                        it.score,
                        it.airDate,
                        it.posterImage,
                        it.backDropImage,
                        it.description,
                        response.page
                    )
                }
                dataBase.tvShowDao().insert(responseMapped)
            } catch (e: Exception) {
                exceptionHandler(e)
            }
        }
    }

    private suspend fun networkCall(page: Int): TvShowsResult =
        when (actualFilter) {
            POPULAR -> tvShowsAPI.getPopularShows(page = page)
            TOP_RATE -> tvShowsAPI.getTopRateShows(page = page)
            ON_AIR -> tvShowsAPI.getOnAirShows(page = page)
            AIRING_TODAY -> tvShowsAPI.getAiringTodayShows(page = page)
        }


    private fun exceptionHandler(e: Exception): RequestResult<Boolean> {
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    401 -> {
                        RequestResult.Failure(Error(null, InternalErrorCodes.BAD_CREDENTIALS))
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