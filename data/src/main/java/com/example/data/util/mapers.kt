package com.example.data.util

import com.example.data.database.entities.*
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
        creators?.joinToString {
            it.name ?: ""
        } ?: NOT_AVAILABLE,
        nextEpisode != null,
        name ?: NOT_AVAILABLE,
        completeUrl(backDropPath),
        Season(
            seasons?.last()?.airDate ?: NOT_AVAILABLE,
            seasons?.last()?.name ?: NOT_AVAILABLE,
            completeUrl(seasons?.last()?.posterPath),
            seasons?.last()?.seasonNumber ?: 0,
            null
        ),
        description ?: NOT_AVAILABLE,
        completeUrl(posterPath),
        score
    )

fun ShowDetailEntity.checkNull(): ShowDetailEntity =
    ShowDetailEntity(
        showId,
        creators,
        nextEpisode ?: EpisodeResponse(),
        name,
        backDropPath,
        seasons,
        description,
        posterPath,
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

fun List<SeasonEntity>.mapSeasonToSeasonDomain(): List<Season> = map {
    Season(
        it.airDate ?: "",
        it.name ?: "",
        completeUrl(it.posterPath),
        it.seasonNumber,
        it.episodes?.mapEpisodeToEpisodeDomain()
    )
}


fun SeasonEntity.mapSeasonToRoom(showId: Int): SeasonEntity =
    SeasonEntity(
        seasonId,
        showId,
        airDate ?: "",
        name ?: "",
        completeUrl(posterPath),
        seasonNumber,
        episodes
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