package com.example.main_screen.domain

import com.example.main_screen.mapers.mergeWith
import com.example.main_screen.mapers.toQuoteData
import com.example.main_screen.presenter.QuoteData
import com.example.network.TradernetApi
import com.example.network.models.QuoteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class QuotesUseCase @Inject constructor(
    private val tradernetApi: TradernetApi
) {

    private val quoteResponseMap: ConcurrentHashMap<String, QuoteResponse> = ConcurrentHashMap()

    suspend fun subscribeToQuotes(ids: List<String>, flow: suspend (Flow<List<QuoteData>>) -> Unit) {
        tradernetApi.initWebSocketSession {
            val listFlow = tradernetApi.getRealtimeQuotes(ids)
                .onEach { newQuoteResponse ->
                    quoteResponseMap[newQuoteResponse.c]?.let { oldQuoteResponse ->
                        quoteResponseMap[newQuoteResponse.c] = oldQuoteResponse.mergeWith(newQuoteResponse)
                    } ?: run { quoteResponseMap[newQuoteResponse.c] = newQuoteResponse }
                }
                .sample(300L)   // use as ticker
                .map {
                    quoteResponseMap.values.map { it.toQuoteData() }.toList()
                }
            flow(listFlow)
        }
    }
}