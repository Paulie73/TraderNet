package com.example.main_screen.mapers

import com.example.main_screen.presenter.DeltaPercentageColoring
import com.example.main_screen.presenter.QuoteData
import com.example.network.models.Direction
import com.example.network.models.QuoteResponse
import com.example.network.models.Sign

fun QuoteResponse.toQuoteData(): QuoteData {
    return QuoteData(
        logoUrl = "",
        name = c,
        description = "$ltr | $name",
        price = "$bap (${chg.plusSignIfNeeded()}$chg)",
        deltaPercentage = "${pcp.plusSignIfNeeded()}$pcp%",
        deltaPercentageColoring = when {
            direction == Direction.UP -> DeltaPercentageColoring.GREEN_BACKGROUND
            direction == Direction.DOWN -> DeltaPercentageColoring.RED_BACKGROUND
            direction == null && sign == Sign.POSITIVE -> DeltaPercentageColoring.GREEN
            direction == null && sign == Sign.NEGATIVE -> DeltaPercentageColoring.RED
            pcp != null && (pcp!! < 0.0) -> DeltaPercentageColoring.RED
            else -> DeltaPercentageColoring.GREEN
        }
    )
}

fun QuoteResponse.mergeWith(newQuoteResponse: QuoteResponse): QuoteResponse {
    return QuoteResponse(
        c = c,
        ltr = ltr,
        name = name,
        bap = newQuoteResponse.bap ?: bap,
        chg = newQuoteResponse.chg ?: chg,
        pcp = newQuoteResponse.pcp ?: pcp,
        direction = when {
            newQuoteResponse.pcp != null && pcp != null && (newQuoteResponse.pcp!! > pcp!!) -> Direction.UP
            newQuoteResponse.pcp != null && pcp != null && (newQuoteResponse.pcp!! < pcp!!) -> Direction.DOWN
            else -> null
        },
        sign = when {
            newQuoteResponse.pcp != null && (newQuoteResponse.pcp!! > 0.0) -> Sign.POSITIVE
            newQuoteResponse.pcp != null && (newQuoteResponse.pcp!! < 0.0) -> Sign.NEGATIVE
            pcp != null && (pcp!! > 0.0) -> Sign.POSITIVE
            pcp != null && (pcp!! < 0.0) -> Sign.NEGATIVE
            else -> null
        }
    )
}

fun Double?.plusSignIfNeeded(): String {
    return if (this != null && (this > 0.0)) "+" else ""
}