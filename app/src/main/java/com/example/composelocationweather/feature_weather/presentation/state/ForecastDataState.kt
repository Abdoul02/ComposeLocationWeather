package com.example.composelocationweather.feature_weather.presentation.state

import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel

data class ForecastDataState(
    val forecastData: ForecastModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = true
)
