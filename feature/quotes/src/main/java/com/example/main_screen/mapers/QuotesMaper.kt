package com.example.main_screen.mapers

import com.example.main_screen.presenter.DeltaPercentageColoring
import com.example.main_screen.presenter.QuoteData
import com.example.network.models.Direction
import com.example.network.models.QuoteResponse
import com.example.network.models.Sign

@Synchronized
fun QuoteResponse.toQuoteData(): QuoteData {
    return QuoteData(
        logoUrl = "",
        name = c,
        description = "$ltr | $name",
        price = "${bap?.format(minStep)} (${chg.plusSignIfNeeded()}${chg?.format(minStep)})",
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

@Synchronized
fun QuoteResponse.mergeWith(newQuoteResponse: QuoteResponse): QuoteResponse {
    return QuoteResponse(
        c = c,
        ltr = ltr,
        name = name,
        bap = newQuoteResponse.bap ?: bap,
        chg = newQuoteResponse.chg ?: chg,
        pcp = newQuoteResponse.pcp ?: pcp,
        minStep = minStep,
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

@Synchronized
fun Double?.plusSignIfNeeded(): String {
    return if (this != null && (this > 0.0)) "+" else ""
}

@Synchronized
fun Double.format(minStep: Double?): String {
    if (minStep == null) return "%.2f".format(this)

    when (this) {
        1.0 -> return "%.0f".format(this)
        0.1 -> return "%.1f".format(this)
        0.01 -> return "%.2f".format(this)
        0.001 -> return "%.3f".format(this)
        0.0001 -> return "%.4f".format(this)
        0.00001 -> return "%.5f".format(this)
        0.000001 -> return "%.6f".format(this)
    }

    val size = minStep.toString().substringAfter(".").length
    return "%.${size}f".format(this)

}