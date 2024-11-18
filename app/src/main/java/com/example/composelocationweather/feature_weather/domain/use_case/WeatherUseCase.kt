package com.example.composelocationweather.feature_weather.domain.use_case

data class WeatherUseCase(
    val getCurrentWeather: GetCurrentWeather,
    val getForecastData: GetForecastData
)