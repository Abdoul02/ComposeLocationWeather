package com.example.composelocationweather.feature_weather.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelocationweather.R
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.Main
import com.example.composelocationweather.util.TestTags


@Composable
fun MaxMinWeatherComponent(
    modifier: Modifier = Modifier,
    currentWeatherInfo: Main,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(0.75f)) {
            TemperatureInfo(
                modifier = Modifier.testTag(TestTags.CURRENT_MIN_TEMP_TEXT),
                info = "min",
                degree = stringResource(
                    R.string.current_temp,
                    currentWeatherInfo.temp_min.toInt().toString(),
                    "\u00B0",
                )
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TemperatureInfo(
                modifier = Modifier.testTag(TestTags.CURRENT_TEMP_INFO_TEXT),
                info = "Current",
                degree = stringResource(
                    R.string.current_temp,
                    currentWeatherInfo.temp.toInt().toString(),
                    "\u00B0"
                )
            )
            TemperatureInfo(
                modifier = Modifier.testTag(TestTags.CURRENT_MAX_TEMP_TEXT),
                info = "max",
                degree = stringResource(
                    R.string.current_temp,
                    currentWeatherInfo.temp_max.toInt().toString(),
                    "\u00B0"
                )
            )
        }
    }
}

@Composable
fun TemperatureInfo(
    modifier: Modifier = Modifier,
    info: String,
    degree: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = degree,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
        Text(text = info, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun MaxMinWeatherComponentPreview() {
    val main = Main(0.0, 0, 0, 23.2, 25.3, 18.0)
    MaxMinWeatherComponent(currentWeatherInfo = main)
}