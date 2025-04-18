package com.example.quotes

import com.example.quotes.models.QuoteResponse
import com.example.quotes.models.TopQuotesResponse
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun getTopQuotes(): TopQuotesResponse

    suspend fun initWebSocketSession(onSuccess: suspend () -> Unit)

    suspend fun getRealtimeQuotes(ids: List<String>): Flow<QuoteResponse>
}