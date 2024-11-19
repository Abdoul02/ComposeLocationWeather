package com.example.composelocationweather.feature_weather.domain.model.forecast

data class ForecastMain(
    val feelLike: Double? = 0.0,
    val grnd_level: Int? = 1,
    val humidity: Int? = 1,
    val pressure: Int? = 1,
    val sea_level: Int? = 1,
    val temp: Double = 0.0,
    val temp_kf: Double? = 0.0,
    val temp_max: Double? = 0.0,
    val temp_min: Double? = 0.0
)