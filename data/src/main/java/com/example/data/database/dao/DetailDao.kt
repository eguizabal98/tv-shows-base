package com.example.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.ShowDetailEntity

@Dao
interface DetailDao {

    @Query("SELECT * FROM tvShowDetail_table WHERE showId=:showId LIMIT 1")
    fun getDetailsShows(showId: Int): LiveData<ShowDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ShowDetailEntity)
}
