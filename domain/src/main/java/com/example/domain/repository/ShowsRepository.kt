package com.example.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.domain.model.FilterType
import com.example.domain.model.TvShow

interface ShowsRepository {
    fun getShows(): LiveData<PagedList<TvShow>>
    fun changeFilter(filterType: FilterType)
    fun setInitialFilter(filterType: FilterType)
}
