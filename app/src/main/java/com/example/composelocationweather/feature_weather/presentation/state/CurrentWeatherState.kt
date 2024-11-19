package com.example.composelocationweather.feature_weather.presentation.state

import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel

data class CurrentWeatherState(
    val currentWeatherData: CurrentWeatherModel
)
