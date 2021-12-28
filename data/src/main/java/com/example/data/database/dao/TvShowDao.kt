package com.example.data.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.TVShowEntity

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tvShows_table")
    fun getPopularShows(): DataSource.Factory<Int, TVShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<TVShowEntity>)

    @Query("DELETE FROM tvShows_table")
    suspend fun clearTvShows()

    @Query("SELECT COUNT(tvShowId)  FROM tvShows_table")
    suspend fun getDataCount(): Int
}
