package com.example.tradernet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.main_screen.presenter.QuotesScreen
import com.example.main_screen.presenter.QuotesViewModel
import com.example.tradernet.ui.theme.TraderNetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: QuotesViewModel by viewModels()
        println()
        setContent {
            TraderNetTheme {
                QuotesScreen()
            }
        }
    }
}