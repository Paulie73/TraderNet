package com.example.main_screen.domain

import android.util.Log
import com.example.network.TradernetApi
import javax.inject.Inject

class QuotesUseCase @Inject constructor(
    private val tradernetApi: TradernetApi
) {

   suspend fun subscribeToQuotes(ids: List<String>) {
       tradernetApi.initWebSocketSession {
           tradernetApi.getRealtimeQuotes(ids)?.collect {
               Log.i("123123", it.toString())
           }
       }
    }
}