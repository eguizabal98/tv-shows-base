package com.example.data.network.requestModels

import com.google.gson.annotations.SerializedName

data class SessionRequestBody(
    @SerializedName("request_token")
    val requestToken: String
)