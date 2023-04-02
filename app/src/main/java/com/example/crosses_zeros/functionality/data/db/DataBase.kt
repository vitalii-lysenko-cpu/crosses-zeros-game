package com.example.crosses_zeros.functionality.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crosses_zeros.functionality.data.entity.LastUrl

@Database(entities = [LastUrl::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun lastUrl(): LastUrlDao
}