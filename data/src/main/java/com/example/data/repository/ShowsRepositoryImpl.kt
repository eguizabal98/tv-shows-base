package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.data.database.DataBase
import com.example.data.paging.ItemBoundaryCallBack
import com.example.data.util.mapToShowDomain
import com.example.domain.model.FilterType
import com.example.domain.model.TvShow
import com.example.domain.repository.ShowsRepository
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val dataBase: DataBase,
    private val itemBoundaryCallBack: ItemBoundaryCallBack
) : ShowsRepository {

    private val config = PagedList.Config.Builder()
        .setPageSize(NETWORK_PAGE_SIZE)
        .setInitialLoadSizeHint(NETWORK_PAGE_SIZE)
        .setPrefetchDistance(HINT_HINT)
        .setEnablePlaceholders(false)
        .build()

    override fun getShows(): LiveData<PagedList<TvShow>> {
        val factory: DataSource.Factory<Int, TvShow> =
            dataBase.tvShowDao().getPopularShows().mapByPage { it.mapToShowDomain() }

        return LivePagedListBuilder(factory, config).setBoundaryCallback(itemBoundaryCallBack)
            .build()
    }

    override fun changeFilter(filterType: FilterType) {
        itemBoundaryCallBack.onChange(filterType)
    }

    override fun setInitialFilter(filterType: FilterType) {
        itemBoundaryCallBack.setInitialFilter(filterType)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val HINT_HINT = 20
    }
}
