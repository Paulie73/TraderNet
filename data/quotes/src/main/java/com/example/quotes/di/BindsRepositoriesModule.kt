package com.example.quotes.di

import com.example.quotes.QuotesRepository
import com.example.quotes.QuotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsRepositoriesModule {

    @Binds
    fun bindQuoteRepository(quotesRepository: QuotesRepositoryImpl): QuotesRepository
}