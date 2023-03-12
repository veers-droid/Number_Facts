package com.example.numberfacts.retrofit.client

import com.example.numberfacts.retrofit.service.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private val BASE_URL = "http://numbersapi.com/"

    val client: RetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}