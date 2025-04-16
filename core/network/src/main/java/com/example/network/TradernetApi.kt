package com.example.network

import com.example.network.Const.TRADING_API_BASE_URL
import com.example.network.models.QuoteResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.wss
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TradernetApi @Inject constructor(
    @KtorHttpClient private val ktorClient: HttpClient,
    @KtorWebSocketClient private val ktorWebSocketClient: HttpClient
) {

//    suspend fun getCountries() {
//        return with(Dispatchers.IO) {
//            ktorClient.get("$CLIENT_API_BASE_URL/country").body()
//        }
//    }

    private var incoming: Flow<QuoteResponse>? = null
    private var outgoing: SendChannel<Frame>? = null

    suspend fun initWebSocketSession(onSuccess: suspend () -> Unit) {
        return withContext(Dispatchers.IO) {
            ktorWebSocketClient.wss(method = HttpMethod.Get, host = TRADING_API_BASE_URL) {
                val jsonWithUnknownKeys = Json { ignoreUnknownKeys = true }

                this@TradernetApi.incoming = incoming
                    .receiveAsFlow()
                    .mapNotNull {
                        runCatching {
                            val receivedText = (it as Frame.Text).readText()
                            val jsonString = receivedText.substringAfter(",").removeSuffix("]")
                            jsonWithUnknownKeys.decodeFromString<QuoteResponse>(jsonString)
                        }.onSuccess {
                            return@mapNotNull it
                        }.onFailure {
                            return@mapNotNull null
                        }
                        return@mapNotNull null
                    }

                this@TradernetApi.outgoing = outgoing
                onSuccess()
            }
        }
    }

    suspend fun getRealtimeQuotes(ids: List<String>): Flow<Any>? {
        withContext(Dispatchers.IO) {
            val joinedString = ids.joinToString(separator = """","""")
            outgoing?.send(Frame.Text("""["realtimeQuotes", ["$joinedString"]]"""))
        }
        return incoming
    }
}