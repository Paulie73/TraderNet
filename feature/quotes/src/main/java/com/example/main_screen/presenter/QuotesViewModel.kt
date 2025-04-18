package com.example.main_screen.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_screen.domain.QuotesUseCase
import com.example.main_screen.domain.TopQuotesUseCase
import com.example.quotes.Const.defaultIds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesUseCase: QuotesUseCase,
    private val topQuotesUseCase: TopQuotesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuotesUIState())
    val uiState: StateFlow<QuotesUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val ids = topQuotesUseCase.getTopQuotes()

            quotesUseCase.subscribeToQuotes(ids) { flow ->
                flow.collect { quotes ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            screenState = ScreenState.PayLoad(
                                quotes = quotes
                            )
                        )
                    }
                }
            }
        }
    }
}