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
            ScreenState.PayLoad(emptyList())
        ),
        QuotesUIState(
            isLoading = true,
            ScreenState.PayLoad(emptyList())
        )
    )
}

sealed interface ScreenState {
    data class PayLoad(
        val quotes: List<String>
    ) : ScreenState

    data class NetworkError(
        val message: String
    )
}
