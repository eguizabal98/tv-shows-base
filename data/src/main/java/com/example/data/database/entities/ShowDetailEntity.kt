package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.network.models.showsdetail.CreatorResponse
import com.example.data.network.models.showsdetail.EpisodeResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvShowDetail_table")
data class ShowDetailEntity(
    @PrimaryKey @SerializedName("id") val showId: Int = 0,
    @SerializedName("created_by") val creators: List<CreatorResponse>? = emptyList(),
    @SerializedName("next_episode_to_air") val nextEpisode: EpisodeResponse? = EpisodeResponse(),
    @SerializedName("name") val name: String? = "",
    @SerializedName("backdrop_path") val backDropPath: String? = "",
    @SerializedName("seasons") val seasons: List<SeasonEntity>? = emptyList(),
    @SerializedName("overview") val description: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("vote_average") val score: Double = 0.0
)
