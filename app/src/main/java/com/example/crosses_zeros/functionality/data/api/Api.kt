package com.example.crosses_zeros.functionality.data.api

import retrofit2.Response
import retrofit2.http.GET

interface Api {
   @GET("/ua")
   suspend fun getErrorResponse(): Response<Unit>
}