package com.example.main_screen.domain

import com.example.quotes.QuotesRepository
import javax.inject.Inject

class TopQuotesUseCase @Inject constructor(
    private val quotesRepository: QuotesRepository
) {

    suspend fun getTopQuotes() {
        quotesRepository.getTopQuotes()
    }
}