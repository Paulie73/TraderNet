package com.example.main_screen.domain

import com.example.network.TradernetApi
import javax.inject.Inject

class TopQuotesUseCase @Inject constructor(
    private val tradernetApi: TradernetApi
) {

    suspend fun getTopQuotes() {
        tradernetApi.getTopQuotes()
    }
}