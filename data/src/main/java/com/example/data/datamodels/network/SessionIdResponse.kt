package com.example.data.datamodels.network

import com.google.gson.annotations.SerializedName

data class SessionIdResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("session_id")
    val sessionId: String)
