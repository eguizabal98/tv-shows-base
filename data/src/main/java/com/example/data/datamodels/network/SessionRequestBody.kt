package com.example.data.datamodels.network

import com.google.gson.annotations.SerializedName

data class SessionRequestBody(
    @SerializedName("request_token")
    val requestToken: String
)