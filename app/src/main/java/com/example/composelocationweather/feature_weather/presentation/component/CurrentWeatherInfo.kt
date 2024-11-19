package com.example.composelocationweather.feature_weather.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composelocationweather.R
import com.example.composelocationweather.util.DateUtils

@Composable
fun CurrentWeatherInfo(
    modifier: Modifier = Modifier,
    temp: String,
    weather: String,
    isOnline: Boolean = true,
    lastSync: Long
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = stringResource(R.string.current_temp, temp, "\u00B0"),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = weather,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )

        if (!isOnline) {
            Text(
                text = DateUtils.getDate(lastSync),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@Preview
@Composable
fun CurrentWeatherInfoPreview() {
    CurrentWeatherInfo(
        temp = "15",
        weather = "CLOUDY",
        lastSync = 1731980275L
    )
}

@Preview
@Composable
fun CurrentWeatherInfoOfflinePreview() {
    CurrentWeatherInfo(
        temp = "15",
        weather = "CLOUDY",
        lastSync = 1731980275L,
        isOnline = false
    )
}