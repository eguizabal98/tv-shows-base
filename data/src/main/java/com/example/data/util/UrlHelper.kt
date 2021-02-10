package com.example.data.util

fun completeUrl(path: String?): String? =
    if (!path.isNullOrEmpty()) {
        "https://image.tmdb.org/t/p/w500$path"
    } else {
        null
    }

fun completeProfileUrl(path: String?): String? =
    if (!path.isNullOrEmpty()) {
        "https://www.gravatar.com/avatar/$path.jpg"
    } else {
        null
    }