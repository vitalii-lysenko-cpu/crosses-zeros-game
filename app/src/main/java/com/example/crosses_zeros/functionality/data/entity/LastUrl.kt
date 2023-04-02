package com.example.crosses_zeros.functionality.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_url")
data class LastUrl(
    val url: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)
