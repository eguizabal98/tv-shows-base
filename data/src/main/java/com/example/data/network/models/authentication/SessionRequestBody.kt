package com.example.data.networkmodels.authentication

import com.google.gson.annotations.SerializedName

data class SessionRequestBody(
    @SerializedName("request_token")
    val requestToken: String
)