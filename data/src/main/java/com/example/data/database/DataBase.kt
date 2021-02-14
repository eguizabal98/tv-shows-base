package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.*
import com.example.data.database.entities.*

@TypeConverters(MyTypeConverters::class)
@Database(
    entities = [TVShowEntity::class, FavoriteShowEntity::class, AccountEntity::class, ShowDetailEntity::class, CreditsEntity::class, SeasonEntity::class],
    version = 9,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
    abstract fun favoriteShowDao(): FavoriteShowDao
    abstract fun accountDao(): AccountDao
    abstract fun detailsDao(): DetailDao
    abstract fun creditsDao(): CreditsDao
    abstract fun seasonDao(): SeasonDao
}