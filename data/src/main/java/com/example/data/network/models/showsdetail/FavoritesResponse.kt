package com.example.data.network.models.showsdetail

import com.example.data.database.entities.FavoriteShowEntity
import com.google.gson.annotations.SerializedName

const val MEDIA_TYPE = "tv"

data class FavoriteRequestBody(
    @SerializedName("media_type") val mediaType: String = MEDIA_TYPE,
    @SerializedName("media_id") val mediaId: Int = 0,
    @SerializedName("favorite") val favorite: Boolean = false
)

data class FavoritesResponse(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("results") val items: List<FavoriteShowEntity>,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0
)
