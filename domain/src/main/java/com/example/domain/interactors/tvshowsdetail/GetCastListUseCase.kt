package com.example.domain.interactors.tvshowsdetail

import com.example.domain.model.Cast

interface GetCastListUseCase {
    suspend fun getCast(showId: Int):List<Cast>
}