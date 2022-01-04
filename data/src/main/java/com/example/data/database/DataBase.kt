package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.AccountDao
import com.example.data.database.dao.CreditsDao
import com.example.data.database.dao.DetailDao
import com.example.data.database.dao.FavoriteShowDao
import com.example.data.database.dao.SeasonDao
import com.example.data.database.dao.TvShowDao
import com.example.data.database.entities.AccountEntity
import com.example.data.database.entities.CreditsEntity
import com.example.data.database.entities.FavoriteShowEntity
import com.example.data.database.entities.SeasonEntity
import com.example.data.database.entities.ShowDetailEntity
import com.example.data.database.entities.TVShowEntity

@TypeConverters(MyTypeConverters::class)
@Database(
    entities = [TVShowEntity::class, FavoriteShowEntity::class, AccountEntity::class, ShowDetailEntity::class, CreditsEntity::class, SeasonEntity::class],
    version = 10,
    exportSchema = true
)
abstract class DataBase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
    abstract fun favoriteShowDao(): FavoriteShowDao
    abstract fun accountDao(): AccountDao
    abstract fun detailsDao(): DetailDao
    abstract fun creditsDao(): CreditsDao
    abstract fun seasonDao(): SeasonDao
}
