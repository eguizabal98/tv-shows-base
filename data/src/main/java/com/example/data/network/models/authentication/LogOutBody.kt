package com.example.data.network.models.authentication

import com.google.gson.annotations.SerializedName

data class LogOutBody(
    @SerializedName("session_id") val sessionId: String
)