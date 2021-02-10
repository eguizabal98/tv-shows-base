package com.example.data.database

import androidx.room.TypeConverter
import com.example.data.network.models.authentication.AvatarResponse
import com.google.gson.Gson

class MyTypeConverters {

    @TypeConverter
    fun avatarResponseToString(avatarResponse: AvatarResponse): String =
        Gson().toJson(avatarResponse)

    @TypeConverter
    fun stringToAppAvatarResponse(string: String): AvatarResponse =
        Gson().fromJson(string, AvatarResponse::class.java)

}