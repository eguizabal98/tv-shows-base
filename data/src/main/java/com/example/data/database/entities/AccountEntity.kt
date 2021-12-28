package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.network.models.authentication.AvatarResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "account_table")
data class AccountEntity(
    @PrimaryKey @SerializedName("id") val accountId: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val userName: String,
    @SerializedName("avatar") val profilePath: AvatarResponse? = null
)
