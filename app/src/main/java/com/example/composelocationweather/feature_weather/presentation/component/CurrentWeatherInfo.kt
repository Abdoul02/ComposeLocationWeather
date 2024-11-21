package com.example.composelocationweather.feature_weather.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelocationweather.R
import com.example.composelocationweather.util.toDateFromUnix

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
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = weather,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        if (!isOnline) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lastSync.toDateFromUnix(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(R.drawable.no_internet),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary)
            }
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