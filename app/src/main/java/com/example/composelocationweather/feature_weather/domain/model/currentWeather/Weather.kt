package com.example.composelocationweather.feature_weather.domain.model.currentWeather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)