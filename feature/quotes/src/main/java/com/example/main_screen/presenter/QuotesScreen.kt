package com.example.main_screen.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuotesScreen(
    viewModel: QuotesViewModel = viewModel<QuotesViewModel>()
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                )
        ) {
            val uiState by viewModel.uiState.collectAsState()
            ScreenContent(uiState, viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenContent(
    @PreviewParameter(QuotesPreview::class) uiState: QuotesUIState,
    viewModel: QuotesViewModel? = null,
) {
    when (uiState.screenState) {
        is ScreenState.PayLoad -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.screenState.quotes) {
                    QuoteItem(quoteData = it)
                }
            }
        }

        is ScreenState.NetworkError -> {

        }

        else -> Unit
    }
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF0F1420),
            )
        }
    }
}

@Composable
fun QuoteItem(quoteData: QuoteData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.TopStart),
            text = quoteData.name,
            fontSize = 18.sp,
            color = Color(0xFF262b2f)
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
                .align(Alignment.BottomStart),
            text = quoteData.description,
            fontSize = 10.sp,
            color = Color(0xFF949398)
        )

        Text(
            modifier = Modifier
                .padding(end = 24.dp, bottom = 8.dp)
                .align(Alignment.BottomEnd),
            text = quoteData.price,
            fontSize = 13.sp,
            color = Color(0xFF262b2f)
        )

        Text(
            modifier = Modifier
                .padding(end = 24.dp, top = 6.dp)
                .align(Alignment.TopEnd)
                .background(
                    color = when (quoteData.deltaPercentageColoring) {
                        DeltaPercentageColoring.GREEN_BACKGROUND -> Color.Green
                        DeltaPercentageColoring.RED_BACKGROUND -> Color.Red
                        DeltaPercentageColoring.RED, DeltaPercentageColoring.GREEN -> Color.Unspecified
                    }, shape = RoundedCornerShape(6.dp)
                )
                .padding(2.dp),
            text = quoteData.deltaPercentage,
            fontSize = 18.sp,
            color = when (quoteData.deltaPercentageColoring) {
                DeltaPercentageColoring.GREEN_BACKGROUND, DeltaPercentageColoring.RED_BACKGROUND -> Color.White
                DeltaPercentageColoring.RED -> Color.Red
                DeltaPercentageColoring.GREEN -> Color.Green
            }
        )

        Icon(
            Icons.Rounded.KeyboardArrowRight, "",
            modifier = Modifier.align(Alignment.CenterEnd),
            tint = Color(0xFF949398)
        )

        Box(
            modifier = Modifier
                .height(1.dp)
                .padding(start = 24.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFededef))
                .align(Alignment.BottomCenter)
        )
    }
}