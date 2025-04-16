package com.example.main_screen.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main_screen.domain.QuotesUseCase
import com.example.network.TradernetApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesUseCase: QuotesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuotesUIState())
    val uiState: StateFlow<QuotesUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quotesUseCase.subscribeToQuotes(listOf("AFLT", "AAPL.US"))
        }
    }
}