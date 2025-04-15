package com.example.main_screen.presenter

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val httpClient: HttpClient
) : ViewModel() {

    init {
        println()
    }
}