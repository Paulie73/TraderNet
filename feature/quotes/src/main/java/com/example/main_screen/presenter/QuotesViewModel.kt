package com.example.main_screen.presenter

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val httpClient: HttpClient
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuotesUIState())
    val uiState: StateFlow<QuotesUIState> = _uiState.asStateFlow()
}