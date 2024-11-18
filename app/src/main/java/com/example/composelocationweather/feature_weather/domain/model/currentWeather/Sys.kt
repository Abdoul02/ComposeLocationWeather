package com.example.composelocationweather.feature_weather.domain.model.currentWeather

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)