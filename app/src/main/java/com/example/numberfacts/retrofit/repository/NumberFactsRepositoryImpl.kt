package com.example.numberfacts.retrofit.repository

import com.example.numberfacts.retrofit.client.RetrofitClient
import javax.inject.Inject

class NumberFactsRepositoryImpl @Inject constructor(
    private val apiClient : RetrofitClient
) : NumberFactsRepository {

    override suspend fun getFactAboutNumber(number: String): String {
        val res = apiClient.client.getFactAboutNumber(number)
        if (res.isSuccessful) {
            return res.body() ?: ""
        }
        return ""
    }

    override suspend fun getFactAboutRandomNumber(): String {
        val res = apiClient.client.getFactAboutRandomNumber()
        if (res.isSuccessful) {
            return res.body() ?: ""
        }
        return ""
    }
}