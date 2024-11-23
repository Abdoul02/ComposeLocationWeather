package com.example.composelocationweather.feature_weather.domain.model.forecast

data class ForecastDetail(
    val clouds: ForecastClouds?,
    val dt: Int,
    val dt_txt: String,
    val main: ForecastMain,
    val rain: Rain?,
    val sys: ForecastSys? = null,
    val weather: List<ForecastWeather>,
    val wind: ForecastWind?
)