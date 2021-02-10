package com.example.data.network.models.authentication

import com.google.gson.annotations.SerializedName

data class AvatarResponse(
    @SerializedName("gravatar") val gravatar: GravatarResponse,
    @SerializedName("tmdb") val tmdbAvatar: TdmdbAvatarResponse
)

data class GravatarResponse(
    @SerializedName("hash") val hash: String
)

data class TdmdbAvatarResponse(
    @SerializedName("avatar_path") val avatarPath: String
)
