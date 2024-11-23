package com.example.composelocationweather.feature_weather.presentation.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IndeterminateCircularIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    if (!isLoading) return

    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Preview
@Composable
fun IndeterminateCircularIndicatorPreview() {
    IndeterminateCircularIndicator(isLoading = true)
}