package com.example.composelocationweather.feature_weather.presentation.weather

import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel

data class CurrentWeatherState(
    val currentWeatherData: CurrentWeatherModel
)
