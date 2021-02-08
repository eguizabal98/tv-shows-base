package com.example.data.util

import com.example.data.database.entities.FavoriteShowEntity
import com.example.data.database.entities.ShowDetailEntity
import com.example.data.database.entities.TVShowEntity
import com.example.data.network.models.showsdetail.CastResponse
import com.example.domain.model.Cast
import com.example.domain.model.Season
import com.example.domain.model.ShowDetails
import com.example.domain.model.TvShow

const val NOT_AVAILABLE = "Not Available"

fun TVShowEntity.mapToShowDomain(): TvShow = TvShow(
    tvShowId,
    name,
    score,
    airDate,
    completeUrl(posterImage),
    completeUrl(backDropImage),
    description,
    page
)

fun List<TVShowEntity>.mapToShowDomain(): List<TvShow> = map {
    it.mapToShowDomain()
}

fun CastResponse.mapToCastDomain(): Cast = Cast(
    name ?: NOT_AVAILABLE,
    character ?: NOT_AVAILABLE,
    completeUrl(profilePath)
)

fun List<CastResponse>.mapToCastDomain(): List<Cast> = map {
    it.mapToCastDomain()
}


fun ShowDetailEntity.mapToShowDetailsDomain(): ShowDetails =
    ShowDetails(
        showId,
        creators.joinToString {
            it.name ?: ""
        },
        nextEpisode != null,
        name ?: NOT_AVAILABLE,
        completeUrl(backDropPath),
        Season(
            seasons.last().airDate ?: NOT_AVAILABLE,
            seasons.last().name ?: NOT_AVAILABLE,
            completeUrl(seasons.last().posterPath),
            seasons.last().seasonNumber
        ),
        description ?: NOT_AVAILABLE,
        completeUrl(posterPath),
        score
    )

fun List<FavoriteShowEntity>.mapFavoriteToShowDomain(): List<TvShow> = map {
    TvShow(
        it.tvShowId,
        it.name,
        it.score,
        it.airDate,
        it.posterImage,
        it.backDropImage,
        it.description,
        it.page
    )
}