package com.example.domain.model

data class TvShow(
    val showId: Int,
    val name: String,
    val score: Double,
    val airDate: String,
    val posterImage: String?,
    val backDropImage: String?,
    val description: String,
    val page:Int
)