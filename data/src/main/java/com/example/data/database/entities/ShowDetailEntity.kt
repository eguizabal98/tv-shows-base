package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.network.models.showsdetail.CreatorResponse
import com.example.data.network.models.showsdetail.EpisodeResponse
import com.example.data.network.models.showsdetail.SeasonResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvShowDetail_table")
data class ShowDetailEntity(
    @PrimaryKey @SerializedName("id") val showId: Int = 0,
    @SerializedName("created_by") val creators: List<CreatorResponse>,
    @SerializedName("next_episode_to_air") val nextEpisode: EpisodeResponse?,
    @SerializedName("name") val name: String? = null,
    @SerializedName("backdrop_path") val backDropPath: String? = null,
    @SerializedName("seasons") val seasons: List<SeasonResponse>,
    @SerializedName("overview") val description: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("vote_average") val score: Double = 0.0
)