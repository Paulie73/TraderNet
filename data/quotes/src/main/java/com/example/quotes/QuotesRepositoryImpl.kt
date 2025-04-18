package com.example.quotes

import com.example.network.KtorHttpClient
import com.example.network.KtorWebSocketClient
import com.example.quotes.models.QuoteResponse
import com.example.quotes.models.TopQuotesRequest
import com.example.quotes.models.TopQuotesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    @KtorHttpClient private val ktorClient: HttpClient,
    @KtorWebSocketClient private val ktorWebSocketClient: HttpClient
) : QuotesRepository {

    override suspend fun getTopQuotes(): TopQuotesResponse {
        return withContext(Dispatchers.IO) {
            ktorClient.post(Const.CLIENT_API_BASE_URL) {
                contentType(ContentType.Application.Json)
                setBody(TopQuotesRequest(q = TopQuotesRequest.Q("getTopSecurities", TopQuotesRequest.Params("stocks", "russia", 0, 30))))
            }.body()
        }
    }

    private var incoming: Flow<QuoteResponse> = MutableStateFlow(QuoteResponse("", "", ""))
    private var outgoing: SendChannel<Frame>? = null

    override suspend fun initWebSocketSession(onSuccess: suspend () -> Unit) {
        return withContext(Dispatchers.IO) {
            ktorWebSocketClient.wss(method = HttpMethod.Get, host = Const.TRADING_API_BASE_URL) {
                val jsonWithUnknownKeys = Json { ignoreUnknownKeys = true }

                this@QuotesRepositoryImpl.incoming = incoming
                    .receiveAsFlow()
                    .mapNotNull {
                        runCatching {
                            val receivedText = (it as Frame.Text).readText()
                            val jsonString = receivedText.substringAfter(",").removeSuffix("]")
//                            Log.i("123123", receivedText)
                            jsonWithUnknownKeys.decodeFromString<QuoteResponse>(jsonString)
                        }.onSuccess {
                            return@mapNotNull it
                        }.onFailure {
                            return@mapNotNull null
                        }
                        return@mapNotNull null
                    }

                this@QuotesRepositoryImpl.outgoing = outgoing
                onSuccess()
            }
        }
    }

    override suspend fun getRealtimeQuotes(ids: List<String>): Flow<QuoteResponse> {
        withContext(Dispatchers.IO) {
            val joinedString = ids.joinToString(separator = """","""")
            outgoing?.send(Frame.Text("""["realtimeQuotes", ["$joinedString"]]"""))
        }
        return incoming
    }
}