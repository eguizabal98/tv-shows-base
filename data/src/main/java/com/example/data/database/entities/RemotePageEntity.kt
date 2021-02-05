package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_page")
data class RemotePageEntity(
    @PrimaryKey
    val showId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)