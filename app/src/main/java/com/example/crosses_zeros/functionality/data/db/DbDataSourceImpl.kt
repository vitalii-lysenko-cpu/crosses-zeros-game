package com.example.crosses_zeros.functionality.data.db

import com.example.crosses_zeros.functionality.data.entity.LastUrl
import javax.inject.Inject

class DbDataSourceImpl @Inject constructor(
    private val lastUrlDao: LastUrlDao
) : DbDataSource {
    override suspend fun getLastUrl(): LastUrl =
        lastUrlDao.getLastUrl()


    override suspend fun insert(lastUrl: LastUrl) {
        lastUrlDao.insert(lastUrl)
    }
}