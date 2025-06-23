package com.example.numberfacts.retrofit.repository

interface NumberFactsRepository {

    suspend fun getFactAboutNumber(number: String): String

    suspend fun getFactAboutRandomNumber(): String
}