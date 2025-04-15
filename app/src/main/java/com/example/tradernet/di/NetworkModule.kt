package com.example.tradernet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideKtorClient(): HttpClient {
        return HttpClient(CIO)
    }
}