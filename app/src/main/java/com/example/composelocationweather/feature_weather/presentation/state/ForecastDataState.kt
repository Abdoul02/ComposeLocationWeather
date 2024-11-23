package com.example.composelocationweather.feature_weather.presentation.state

import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastDetail

data class ForecastDataState(
    val forecastDetails: List<ForecastDetail>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = true
)
