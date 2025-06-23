package com.example.numberfacts.retrofit.repository


/**
 * Number facts repository where we have 2 main functions to get fact about number
 */
interface NumberFactsRepository {

    suspend fun getFactAboutNumber(number: String): String

    suspend fun getFactAboutRandomNumber(): String
}