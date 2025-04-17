package com.example.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    val c: String,
    val ltr: String? = null,
    val name: String? = null,
    val bap: Double? = null,
    val chg: Double? = null,
    val pcp: Double? = null,
    @SerialName("min_step") val minStep: Double? = null,
    val direction: Direction? = null,
    val sign: Sign? = null
)

@Serializable
enum class Direction {
    UP, DOWN
}

@Serializable
enum class Sign {
    POSITIVE, NEGATIVE
}
