package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.TvShowDao
import com.example.data.database.entities.FavoriteShowEntity
import com.example.data.database.entities.TVShowEntity

@Database(
    entities = [TVShowEntity::class, FavoriteShowEntity::class],
    version = 5,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
    abstract fun favoriteShowDao(): FavoriteShowDao
}