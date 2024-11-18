package com.example.composelocationweather.feature_weather.domain.model.forecast

data class ForecastWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)