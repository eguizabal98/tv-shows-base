package com.example.data.network.models.season

import com.example.data.network.models.showsdetail.EpisodeResponse
import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    @SerializedName("air_date") val airDate: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("season_number") val seasonNumber: Int = 0,
    @SerializedName("episodes") val episodes: List<EpisodeResponse>? = null
)

