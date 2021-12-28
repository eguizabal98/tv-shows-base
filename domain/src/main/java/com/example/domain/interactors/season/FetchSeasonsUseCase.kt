package com.example.domain.interactors.season

import com.example.domain.model.RequestResult

interface FetchSeasonsUseCase {
    suspend fun getSeasons(showsId: Int, seasons: Int): RequestResult<Boolean>
}
