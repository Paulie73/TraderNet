package com.example.quotes

import com.example.quotes.models.QuoteResponse
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun getTopQuotes()

    suspend fun initWebSocketSession(onSuccess: suspend () -> Unit)

    suspend fun getRealtimeQuotes(ids: List<String>): Flow<QuoteResponse>
}