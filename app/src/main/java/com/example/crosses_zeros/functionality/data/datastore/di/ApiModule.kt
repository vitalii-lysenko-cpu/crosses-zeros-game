package com.example.crosses_zeros.functionality.data.datastore.di

import com.example.crosses_zeros.functionality.data.api.Api
import com.example.crosses_zeros.functionality.data.api.ApiDataSource
import com.example.crosses_zeros.functionality.data.api.ApiDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

const val BASE_URL = "https://dok.ua"

@Module
@InstallIn(SingletonComponent::class)
internal interface ApiModule {

    @Binds
    fun bindApiDataSource(impl: ApiDataSourceImpl): ApiDataSource

    companion object {
        private lateinit var api: Api

        @Reusable
        @Provides
        fun provideNumberApi(): Api {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient(httpLoggingInterceptor()))
                .build()
            api = retrofit.create(Api::class.java)
            return api
        }

        @Provides
        fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        @Provides
        fun httpLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }
    }
}