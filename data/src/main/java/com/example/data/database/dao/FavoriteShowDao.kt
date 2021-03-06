package com.example.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.FavoriteShowEntity

@Dao
interface FavoriteShowDao {
    @Query("SELECT * FROM favorites_table")
    fun getFavoritesShows(): LiveData<List<FavoriteShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<FavoriteShowEntity>)

    @Query("DELETE FROM favorites_table WHERE tvShowId = :showId")
    suspend fun delete(showId: Int)

    @Query("DELETE FROM favorites_table")
    suspend fun clearBase()
}