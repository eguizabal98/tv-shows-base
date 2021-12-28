package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites_table")
data class FavoriteShowEntity(
    @SerializedName("id") val tvShowId: Int,
    @PrimaryKey @SerializedName("name") val name: String,
    @SerializedName("vote_average") val score: Double,
    @SerializedName("first_air_date") val airDate: String,
    @SerializedName("poster_path") val posterImage: String?,
    @SerializedName("backdrop_path") val backDropImage: String?,
    @SerializedName("overview") val description: String,
    val page: Int
)
