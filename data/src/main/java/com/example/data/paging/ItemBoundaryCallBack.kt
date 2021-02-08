package com.example.data.paging

import android.util.Log
import androidx.paging.PagedList
import androidx.room.withTransaction
import com.example.data.database.DataBase
import com.example.data.database.entities.TVShowEntity
import com.example.data.network.models.showslist.TvShowsResult
import com.example.data.network.api.TvShowsAPI
import com.example.domain.model.FilterType
import com.example.domain.model.FilterType.*
import com.example.domain.model.TvShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val INITIAL_PAGE = 1
const val NEXT_PAGE_RANGE = 1

class ItemBoundaryCallBack(
    private val tvShowsAPI: TvShowsAPI,
    private val dataBase: DataBase,
    private val scope: CoroutineScope
) :
    PagedList.BoundaryCallback<TvShow>(), CategoryCallback {

    private var actualFilter: FilterType = POPULAR
    private var lastFilter: FilterType = POPULAR

    override fun setInitialFilter(filterType: FilterType) {
        actualFilter = filterType
        lastFilter = filterType
    }

    override fun onChange(filterType: FilterType) {
        Log.d("ItemBoundaryCallBack", "On change-----------------")
        actualFilter = filterType
        if (actualFilter != lastFilter) {
            lastFilter = actualFilter
            scope.launch {
                actualFilter = filterType
                val items = dataBase.withTransaction {
                    dataBase.tvShowDao().clearTvShows()
                    dataBase.tvShowDao().getDataCount()
                }
                Log.d("ItemBoundaryCallBack", "Items on clear $items")
                onZeroItemsLoaded()
            }
        }
    }

    override fun onZeroItemsLoaded() {
        Log.d("ItemBoundaryCallBack", "onZeroItemsLoaded")
        scope.launch {
            try {
                if (dataBase.tvShowDao().getDataCount() == 0) {
                    Log.d("ItemBoundaryCallBack", "Empty")
                    val response = networkCall(INITIAL_PAGE)
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
            } catch (e: IOException) {
                Log.e("ItemBoundaryCallBack", "e: ${e.message}")
            } catch (e: HttpException) {
                Log.e("ItemBoundaryCallBack", "e: ${e.message}")
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: TvShow) {
        Log.d("ItemBoundaryCallBack", "onItemAtEndLoaded")
        scope.launch {
            val page = itemAtEnd.page + NEXT_PAGE_RANGE
            Log.d("ItemBoundaryCallBack", "Page $page")
            try {
                val response = networkCall(page)
                Log.d("ItemBoundaryCallBack", "item coming ${response.items.size}")
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
            } catch (e: IOException) {
                Log.e("ItemBoundaryCallBack", "e: ${e.message}")
            } catch (e: HttpException) {
                Log.e("ItemBoundaryCallBack", "e: ${e.message}")
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

}