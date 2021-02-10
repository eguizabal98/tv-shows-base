package com.example.data.repository

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("success") val success: Boolean?
)
