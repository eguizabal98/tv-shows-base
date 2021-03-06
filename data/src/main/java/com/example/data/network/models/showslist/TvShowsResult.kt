package com.example.data.network.models.showslist

import com.example.data.database.entities.TVShowEntity
import com.google.gson.annotations.SerializedName

data class TvShowsResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val items: List<TVShowEntity>
)
