package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("success") val success: Boolean?
)
