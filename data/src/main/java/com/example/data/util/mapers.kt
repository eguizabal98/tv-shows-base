package com.example.data.util

import com.example.data.database.entities.AccountEntity
import com.example.data.database.entities.FavoriteShowEntity
import com.example.data.database.entities.ShowDetailEntity
import com.example.data.database.entities.TVShowEntity
import com.example.data.network.models.season.SeasonResponse
import com.example.data.network.models.showsdetail.CastResponse
import com.example.data.network.models.showsdetail.EpisodeResponse
import com.example.domain.model.*

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
            seasons.last().seasonNumber,
            null
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
        completeUrl(it.posterImage),
        completeUrl(it.backDropImage),
        it.description,
        it.page
    )
}

fun SeasonResponse.mapSeasonToSeasonDomain(): Season =
    Season(
        airDate ?: "",
        name ?: "",
        completeUrl(posterPath),
        seasonNumber,
        episodes?.mapEpisodeToEpisodeDomain()
    )

fun EpisodeResponse.mapToDomain() = Episode(
    id,
    airDate ?: "",
    name ?: "",
    completeUrl(stillPath)
)

fun List<EpisodeResponse>.mapEpisodeToEpisodeDomain() = map { it.mapToDomain() }

fun AccountEntity.mapToProfileDomain(): Profile =
    Profile(
        accountId,
        name ?: NOT_AVAILABLE,
        userName,
        if (profilePath?.tmdbAvatar == null) {
            completeProfileUrl(profilePath?.gravatar?.hash) ?: NOT_AVAILABLE
        } else {
            completeUrl(profilePath.tmdbAvatar.avatarPath) ?: NOT_AVAILABLE
        }
    )