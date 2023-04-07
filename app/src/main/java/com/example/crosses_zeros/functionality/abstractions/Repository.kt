package com.example.crosses_zeros.functionality.abstractions

interface Repository {
    suspend fun getErrorResponse(): Int

    //    suspend fun getLastUrl(): String
    fun checkConnection(): Boolean
}