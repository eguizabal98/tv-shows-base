package com.example.data.network.models.showsdetail

import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("id") val castId: Int = 0,
    @SerializedName("name") val name: String? = null,
    @SerializedName("character") val character: String? = null,
    @SerializedName("profile_path") val profilePath: String? = null
)
