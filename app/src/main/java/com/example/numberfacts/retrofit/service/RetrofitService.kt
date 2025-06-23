package com.example.numberfacts.retrofit.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("{number}")
    suspend fun getFactAboutNumber(@Path("number") number: String): Response<String>

    @GET("random/math")
    suspend fun getFactAboutRandomNumber(): Response<String>
}