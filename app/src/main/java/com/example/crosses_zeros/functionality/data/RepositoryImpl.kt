package com.example.crosses_zeros.functionality.data

import com.example.crosses_zeros.functionality.abstractions.Repository
import com.example.crosses_zeros.functionality.data.api.ApiDataSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource
): Repository {
    override suspend fun getErrorResponse(): Int = apiDataSource.getErrorResponse()
//    override suspend fun getLastUrl(): String = apiDataSource.getLastUrl()

    override fun checkConnection(): Boolean = apiDataSource.checkConnection()
}