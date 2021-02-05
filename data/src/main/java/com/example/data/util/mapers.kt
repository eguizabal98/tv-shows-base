package com.example.data.util

import com.example.data.database.entities.TVShowEntity
import com.example.domain.model.TvShow

fun TVShowEntity.mapToModel(): TvShow = TvShow(
    tvShowId,
    name,
    score,
    airDate,
    completeUrl(posterImage),
    completeUrl(backDropImage),
    description
)

fun List<TVShowEntity>.mapToModel(): List<TvShow> = map {
    it.mapToModel()
}