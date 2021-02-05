package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.RemotePageEntity

@Dao
interface RemotePageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemotePageEntity>)

    @Query("SELECT * FROM remote_page WHERE showId = :showId")
    suspend fun remotePageShowId(showId: Int): RemotePageEntity?

    @Query("DELETE FROM remote_page")
    suspend fun clearRemotePages()
}