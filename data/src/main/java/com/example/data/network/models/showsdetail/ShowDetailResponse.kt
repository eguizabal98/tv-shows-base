package com.example.data.network.models.showsdetail

import com.google.gson.annotations.SerializedName


data class CreatorResponse(
    @SerializedName("id") val creatorId: Int = 0,
    @SerializedName("name") val name: String? = null,
    @SerializedName("profile_path") val profileImage: String? = null
)

data class EpisodeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("air_date") val airDate: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("still_path") val stillPath: String? = null
)



