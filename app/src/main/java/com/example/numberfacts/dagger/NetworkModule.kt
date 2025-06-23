package com.example.numberfacts.dagger

import com.example.numberfacts.retrofit.client.RetrofitClient
import com.example.numberfacts.retrofit.repository.NumberFactsRepository
import com.example.numberfacts.retrofit.repository.NumberFactsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiClient() : RetrofitClient = RetrofitClient

    @Provides
    fun provideFactsRepository(apiClient : RetrofitClient) : NumberFactsRepository = NumberFactsRepositoryImpl(apiClient)

}