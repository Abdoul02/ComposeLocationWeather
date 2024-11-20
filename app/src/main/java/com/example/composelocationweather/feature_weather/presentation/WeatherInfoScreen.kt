package com.example.composelocationweather.feature_weather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.composelocationweather.R
import com.example.composelocationweather.feature_weather.domain.model.enums.WeatherTypes
import com.example.composelocationweather.feature_weather.presentation.component.CurrentWeatherInfo
import com.example.composelocationweather.feature_weather.presentation.component.CustomFloatingActionButton
import com.example.composelocationweather.feature_weather.presentation.component.ForecastItem
import com.example.composelocationweather.feature_weather.presentation.component.IndeterminateCircularIndicator
import com.example.composelocationweather.feature_weather.presentation.component.MaxMinWeatherComponent
import com.example.composelocationweather.feature_weather.presentation.state.CurrentWeatherState
import com.example.composelocationweather.feature_weather.presentation.state.ForecastDataState
import com.example.composelocationweather.ui.theme.Cloudy
import com.example.composelocationweather.ui.theme.Rainy
import com.example.composelocationweather.ui.theme.Sunny

@Composable
fun WeatherInfoScreen(
    currentWeatherState: State<CurrentWeatherState>,
    forecastDataState: State<ForecastDataState>,
    isDeviceOnline: Boolean
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentWeatherData = currentWeatherState.value
    val forecastData = forecastDataState.value


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                CustomFloatingActionButton(expandable = true) {

                }
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IndeterminateCircularIndicator(isLoading = currentWeatherData.isLoading)

                if (currentWeatherData.currentWeatherModel?.weather?.isNotEmpty() == true) {
                    val background =
                        getBackground(currentWeatherData.currentWeatherModel.weather[0].main)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .paint(
                                painterResource(background.first),
                                contentScale = ContentScale.FillBounds
                            )
                            .weight(1f),
                    ) {
                        CurrentWeatherInfo(
                            modifier = Modifier.align(Alignment.Center),
                            temp = currentWeatherData.currentWeatherModel.main.temp.toInt()
                                .toString(),
                            weather = currentWeatherData.currentWeatherModel.weather[0].main,
                            lastSync = currentWeatherData.currentWeatherModel.dt.toLong(),
                            isOnline = isDeviceOnline
                        )

                        MaxMinWeatherComponent(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            currentWeatherInfo = currentWeatherData.currentWeatherModel.main
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = background.second)
                            .padding(innerPadding)
                            .weight(1f),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        IndeterminateCircularIndicator(isLoading = forecastData.isLoading)
                        Column {

                            forecastData.forecastDetails?.let { forecast ->
                                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                    items(forecast) { forecastDetail ->
                                        ForecastItem(forecastDetail = forecastDetail)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getBackground(temperatureDetail: String): Pair<Int, Color> {
    return when (temperatureDetail) {
        WeatherTypes.CLEAR.types -> Pair(R.drawable.forest_sunny, Sunny) //No image for Clear

        WeatherTypes.CLOUD.types -> Pair(R.drawable.forest_cloudy, Cloudy)

        WeatherTypes.RAIN.types -> Pair(R.drawable.forest_rainy, Rainy)

        else -> Pair(R.drawable.forest_sunny, Sunny)
    }
}