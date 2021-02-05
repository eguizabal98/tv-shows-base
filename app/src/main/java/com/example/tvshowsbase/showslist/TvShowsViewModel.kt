package com.example.tvshowsbase.showslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.domain.interactors.tvshowslist.GetTvShowsUseCase
import com.example.domain.model.FilterType
import com.example.domain.model.TvShow

class TvShowsViewModel(getTvShowsUseCase: GetTvShowsUseCase<LiveData<PagedList<TvShow>>>) :
    ViewModel() {

    val showsList: LiveData<PagedList<TvShow>> =
        getTvShowsUseCase.getPopularShows(FilterType.TOP_RATE)

}