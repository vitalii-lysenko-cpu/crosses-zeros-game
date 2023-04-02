package com.example.crosses_zeros.functionality.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crosses_zeros.functionality.data.entity.LastUrl

@Dao
interface LastUrlDao {
    @Query("SELECT * FROM last_url ORDER BY id DESC LIMIT 1")
    suspend fun getLastUrl(): LastUrl

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lastUrl: LastUrl)
}