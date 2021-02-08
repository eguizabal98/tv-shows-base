package com.example.data.network.models.showsdetail

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CreatorResponse(
    @PrimaryKey @SerializedName("id") val creatorId: Int = 0,
    @SerializedName("name") val name: String? = null,
    @SerializedName("profile_path") val profileImage: String? = null
)

@Entity
data class EpisodeResponse(
    @PrimaryKey @SerializedName("air_date") val airDate: String? = null
)

@Entity
data class SeasonResponse(
    @PrimaryKey @SerializedName("air_date") val airDate: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("season_number") val seasonNumber: Int = 0
)


