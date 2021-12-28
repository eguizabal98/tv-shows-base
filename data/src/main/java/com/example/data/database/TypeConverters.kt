package com.example.data.database

import androidx.room.TypeConverter
import com.example.data.database.entities.SeasonEntity
import com.example.data.network.models.authentication.AvatarResponse
import com.example.data.network.models.showsdetail.CastResponse
import com.example.data.network.models.showsdetail.CreatorResponse
import com.example.data.network.models.showsdetail.EpisodeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyTypeConverters {

    @TypeConverter
    fun avatarResponseToString(avatarResponse: AvatarResponse): String =
        Gson().toJson(avatarResponse)

    @TypeConverter
    fun stringToAppAvatarResponse(string: String): AvatarResponse =
        Gson().fromJson(string, AvatarResponse::class.java)

    @TypeConverter
    fun creatorToString(creator: List<CreatorResponse>): String =
        Gson().toJson(creator)

    @TypeConverter
    fun stringToCreator(string: String): List<CreatorResponse> {
        val listType = object : TypeToken<List<CreatorResponse>>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun episodeToString(episodeResponse: EpisodeResponse): String =
        Gson().toJson(episodeResponse)

    @TypeConverter
    fun stringToEpisode(string: String): EpisodeResponse =
        Gson().fromJson(string, EpisodeResponse::class.java)

    @TypeConverter
    fun episodesListToString(episodeResponse: List<EpisodeResponse>): String =
        Gson().toJson(episodeResponse)

    @TypeConverter
    fun stringToEpisodesList(string: String): List<EpisodeResponse> {
        val listType = object : TypeToken<List<EpisodeResponse>>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun seasonListToString(seasonResponse: List<SeasonEntity>): String =
        Gson().toJson(seasonResponse)

    @TypeConverter
    fun stringToSeasonList(string: String): List<SeasonEntity> {
        val listType = object : TypeToken<List<SeasonEntity>>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun castToString(cast: List<CastResponse>): String =
        Gson().toJson(cast)

    @TypeConverter
    fun stringToCast(string: String): List<CastResponse> {
        val listType = object : TypeToken<List<CastResponse>>() {}.type
        return Gson().fromJson(string, listType)
    }
}
