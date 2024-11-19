package com.example.composelocationweather.feature_weather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composelocationweather.R
import com.example.composelocationweather.feature_weather.presentation.component.CurrentWeatherInfo
import com.example.composelocationweather.feature_weather.presentation.component.ForecastItem
import com.example.composelocationweather.feature_weather.presentation.component.IndeterminateCircularIndicator
import com.example.composelocationweather.ui.theme.Cloudy

@Composable
fun WeatherInfoScreen(
    //onNavigateToFavourite: () -> Unit,
    viewModel: GetWeatherViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentWeatherData = viewModel.currentWeatherState.value
    val forecastData = viewModel.forecastState.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .paint(painterResource(R.drawable.forest_cloudy), contentScale = ContentScale.FillBounds)
                        .padding(10.dp)
                        .weight(1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    IndeterminateCircularIndicator(isLoading = currentWeatherData.isLoading)

                    if (!currentWeatherData.isLoading) {
                        currentWeatherData.currentWeatherData?.let { weather ->
                            CurrentWeatherInfo(
                                temp = weather.main.temp.toInt().toString(),
                                weather = weather.weather[0].main,
                                lastSync = weather.dt.toLong()
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Cloudy)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    IndeterminateCircularIndicator(isLoading = forecastData.isLoading)
                    forecastData.forecastData?.let { forecastModel ->
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(forecastModel.list.distinctBy { it.dt_txt }) { forecastDetail ->
                                ForecastItem(forecastDetail = forecastDetail)
                            }
                        }
                    }
                }
            }
        }
    }
}