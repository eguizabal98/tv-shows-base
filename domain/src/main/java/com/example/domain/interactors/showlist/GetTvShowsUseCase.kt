package com.example.domain.interactors.showlist

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.domain.model.TvShow

interface GetTvShowsUseCase {
    fun getShows(): LiveData<PagedList<TvShow>>
}
