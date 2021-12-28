package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM account_table LIMIT 1")
    suspend fun getAccount(): AccountEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: AccountEntity)

    @Query("DELETE FROM account_table")
    suspend fun clearBase()
}
