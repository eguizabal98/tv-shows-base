package com.example.domain.interactors.showlist

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.domain.model.TvShow
import com.example.domain.repository.ShowsRepository
import javax.inject.Inject

class GetTvShowsUseCaseImpl @Inject constructor(private val showsRepository: ShowsRepository) :
    GetTvShowsUseCase {
    override fun getShows(): LiveData<PagedList<TvShow>> {
        return showsRepository.getShows()
    }
}
