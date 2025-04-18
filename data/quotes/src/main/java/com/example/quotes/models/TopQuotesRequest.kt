package com.example.quotes.models

import kotlinx.serialization.Serializable

@Serializable
data class TopQuotesRequest(
    val cmd: String,
    val params: Params,
) {
    @Serializable
    data class Params(
        val type: String,
        val exchange: String,
        val gainers: Int,
        val limit: Int,
        val apiKey: String
    )
}
