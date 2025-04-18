package com.example.quotes.models

import kotlinx.serialization.Serializable

@Serializable
data class TopQuotesResponse(
    val tickers: List<String>
)
