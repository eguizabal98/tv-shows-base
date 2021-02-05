package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.RemotePageDao
import com.example.data.database.dao.TvShowDao
import com.example.data.database.entities.RemotePageEntity
import com.example.data.database.entities.TVShowEntity

@Database(
    entities = [TVShowEntity::class, RemotePageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
    abstract fun remotePageDao(): RemotePageDao
}