package com.example.crosses_zeros.functionality.data

import com.example.crosses_zeros.functionality.abstractions.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository
}