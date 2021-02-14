package com.example.domain.model

data class ShowDetails(
    val showId: Int,
    val creators: String,
    val airing: Boolean,
    val name: String,
    val backDropPath: String?,
    val lastSeason: Season,
    val description: String,
    val posterPath: String?,
    val score: Double
)
