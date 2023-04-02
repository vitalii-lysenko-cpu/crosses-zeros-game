package com.example.crosses_zeros.functionality.data.db

import com.example.crosses_zeros.functionality.data.entity.LastUrl

interface DbDataSource {
    suspend fun getLastUrl(): LastUrl
    suspend fun insert(lastUrl: LastUrl)
}