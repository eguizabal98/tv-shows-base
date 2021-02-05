package com.example.data.paging

import android.util.Log
import androidx.paging.PagedList
import androidx.room.withTransaction
import com.example.data.database.DataBase
import com.example.data.datamodels.network.TvShowsResult
import com.example.data.network.TvShowsAPI
import com.example.domain.model.FilterType
import com.example.domain.model.FilterType.*
import com.example.domain.model.TvShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val PAGE_SIZE = 20
const val INITIAL_PAGE = 1
const val NEXT_PAGE_RANGE = 1

class ItemBoundaryCallBack(
    private val tvShowsAPI: TvShowsAPI,
    private val dataBase: DataBase,
    private val scope: CoroutineScope
) :
    PagedList.BoundaryCallback<TvShow>(), CategoryCallback {

    private var actualFilter: FilterType = POPULAR

    override fun onChange(filterType: FilterType) {
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

    override fun onZeroItemsLoaded() {
        Log.d("ItemBoundaryCallBack", "onZeroItemsLoaded")
        scope.launch {
            try {
                if (dataBase.tvShowDao().getDataCount() == 0) {
                    Log.d("ItemBoundaryCallBack", "Empty")
                    val response = networkCall(INITIAL_PAGE)
                    dataBase.tvShowDao().insert(response.items)
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
            val items = dataBase.tvShowDao().getDataCount()
            val actualPages: Int = items / PAGE_SIZE
            val page = actualPages + NEXT_PAGE_RANGE
            Log.d("ItemBoundaryCallBack", "Page $actualPages")
            try {
                val response = networkCall(page)
                dataBase.tvShowDao().insert(response.items)
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