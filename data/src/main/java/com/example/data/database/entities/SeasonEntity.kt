package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.network.models.showsdetail.EpisodeResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "season_table")
data class SeasonEntity(
    @PrimaryKey @SerializedName("id") val seasonId: Int,
    val showId: Int,
    @SerializedName("air_date") val airDate: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("season_number") val seasonNumber: Int = 0,
    @SerializedName("episodes") val episodes: List<EpisodeResponse>? = emptyList()
)