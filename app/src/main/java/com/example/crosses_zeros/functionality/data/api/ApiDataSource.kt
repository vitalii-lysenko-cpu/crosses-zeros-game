package com.example.crosses_zeros.functionality.data.api

interface ApiDataSource {
    suspend fun getErrorResponse(): Int
//    suspend fun getLastUrl(): String
    fun checkConnection(): Boolean
}