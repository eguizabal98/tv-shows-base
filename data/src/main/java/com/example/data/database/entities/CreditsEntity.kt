package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.network.models.showsdetail.CastResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "credits_table")
data class CreditsEntity(
    @PrimaryKey @SerializedName("id") val creditsId: Int = 0,
    @SerializedName("cast") val castItems: List<CastResponse>
)
