package com.example.data.util

fun completeUrl(path: String?): String? =
    if (!path.isNullOrEmpty()) {
        "https://image.tmdb.org/t/p/w500$path"
    } else {
        null
    }