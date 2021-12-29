package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.SeasonEntity

@Dao
interface SeasonDao {
    @Query("SELECT * FROM season_table WHERE showId=:showId ")
    suspend fun getSeasons(showId: Int): List<SeasonEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<SeasonEntity>)
}
