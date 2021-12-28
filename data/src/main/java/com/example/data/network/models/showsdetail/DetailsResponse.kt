package com.example.data.network.models.showsdetail

import com.google.gson.annotations.SerializedName

data class CreatorResponse(
    @SerializedName("id") val creatorId: Int = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("profile_path") val profileImage: String? = ""
)

data class EpisodeResponse(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("air_date") val airDate: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("still_path") val stillPath: String? = ""
)
