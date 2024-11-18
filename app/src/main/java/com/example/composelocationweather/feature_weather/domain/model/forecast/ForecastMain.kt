package com.example.composelocationweather.feature_weather.domain.model.forecast

data class ForecastMain(
    val feelLike: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Double,
    val temp_max: Double,
    val temp_min: Double
)