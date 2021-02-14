package com.example.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.CreditsEntity

@Dao
interface CreditsDao {
    @Query("SELECT * FROM credits_table WHERE creditsId=:showId LIMIT 1")
    fun getCreditsShows(showId: Int): LiveData<CreditsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: CreditsEntity)
}