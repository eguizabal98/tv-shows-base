package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.AccountDao
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.TvShowDao
import com.example.data.database.entities.AccountEntity
import com.example.data.database.entities.FavoriteShowEntity
import com.example.data.database.entities.TVShowEntity

@TypeConverters(MyTypeConverters::class)
@Database(
    entities = [TVShowEntity::class, FavoriteShowEntity::class, AccountEntity::class],
    version = 7,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
    abstract fun favoriteShowDao(): FavoriteShowDao
    abstract fun accountDao(): AccountDao
}