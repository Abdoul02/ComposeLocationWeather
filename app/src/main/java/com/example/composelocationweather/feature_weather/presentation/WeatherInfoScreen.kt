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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.composelocationweather.R
import com.example.composelocationweather.common.component.IndeterminateCircularIndicator
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_weather.domain.model.enums.WeatherTypes
import com.example.composelocationweather.feature_weather.presentation.component.CurrentWeatherInfo
import com.example.composelocationweather.feature_weather.presentation.component.CustomFloatingActionButton
import com.example.composelocationweather.feature_weather.presentation.component.ForecastItem
import com.example.composelocationweather.feature_weather.presentation.component.MaxMinWeatherComponent
import com.example.composelocationweather.feature_weather.presentation.state.CurrentWeatherState
import com.example.composelocationweather.feature_weather.presentation.state.ForecastDataState
import com.example.composelocationweather.ui.theme.Cloudy
import com.example.composelocationweather.ui.theme.Rainy
import com.example.composelocationweather.ui.theme.Sunny
import com.example.composelocationweather.util.Screens
import com.example.composelocationweather.util.TestTags
import kotlinx.coroutines.launch

@Composable
fun WeatherInfoScreen(
    navController: NavController,
    currentWeatherState: State<CurrentWeatherState>,
    forecastDataState: State<ForecastDataState>,
    isDeviceOnline: Boolean,
    onLocationSave: (UserLocation) -> Unit
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
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.testTag(TestTags.WEATHER_SCREEN_SNACK_BAR)
                )
            },
            floatingActionButton = {
                CustomFloatingActionButton(
                    expandable = true,
                    onFabClick = {},
                    onSaveLocation = {
                        currentWeatherData.currentWeatherModel?.let { weatherModel ->
                            val location = UserLocation(
                                id = weatherModel.id,
                                latitude = weatherModel.coord.lat,
                                longitude = weatherModel.coord.lon,
                                name = weatherModel.name
                            )
                            onLocationSave(location)
                            scope.launch {
                                snackbarHostState.showSnackbar("Location saved!")
                            }
                        }

                    },
                    onGotoMap = {
                        navController.navigate(Screens.MapScreen)
                    },
                    onGotoLocations = {
                        navController.navigate(Screens.LocationScreen)
                    }
                )
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
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag(TestTags.FORECAST_LIST)
                                ) {
                                    items(forecast) { forecastDetail ->
                                        ForecastItem(forecastDetail = forecastDetail)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    val message = currentWeatherData.errorMessage ?: "Getting current weather"
                    Text(text = message)
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