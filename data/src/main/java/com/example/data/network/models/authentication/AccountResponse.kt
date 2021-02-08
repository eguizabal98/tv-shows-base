package com.example.data.network.models.authentication

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("id")
    val accountId: Int
)