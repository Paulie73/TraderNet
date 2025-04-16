package com.example.main_screen.presenter

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class QuotesUIState(
    val isLoading: Boolean = true,
    val screenState: ScreenState? = null,
)

class QuotesPreview : PreviewParameterProvider<QuotesUIState> {
    override val values: Sequence<QuotesUIState> = sequenceOf(
        QuotesUIState(
            isLoading = false,
            ScreenState.PayLoad(
                let {
                    val quotes = mutableListOf<QuoteData>()
                    repeat(30) {
                        quotes.add(
                            QuoteData(
                                logoUrl = "",
                                name = "FEES",
                                description = "МСХ | ФСК ЕЭС ао",
                                price = "0.210 76 ( +0.006 88 )",
                                deltaPercentage = "+3.37%",
                                deltaPercentageColoring = DeltaPercentageColoring.GREEN_BACKGROUND
                            )
                        )
                    }
                    quotes
                }
            )
        ),
        QuotesUIState(
            isLoading = true,
            ScreenState.PayLoad(emptyList())
        )
    )
}

sealed interface ScreenState {
    data class PayLoad(
        val quotes: List<QuoteData>
    ) : ScreenState

    data class NetworkError(
        val message: String
    ) : ScreenState
}

data class QuoteData(
    val logoUrl: String,
    val name: String,
    val description: String,
    val price: String,
    val deltaPercentage: String,
    val deltaPercentageColoring: DeltaPercentageColoring
)

enum class DeltaPercentageColoring {
    GREEN, RED, GREEN_BACKGROUND, RED_BACKGROUND
}