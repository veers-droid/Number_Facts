package com.example.numberfacts.retrofit.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("{number}")
    fun getFactAboutNumber(@Path("number") number: String): Call<String>

    @GET("random/math")
    fun getFactAboutRandomNumber(): Call<String>
}