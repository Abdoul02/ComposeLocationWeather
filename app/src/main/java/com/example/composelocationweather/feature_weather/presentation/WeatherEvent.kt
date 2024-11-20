package com.example.composelocationweather.feature_weather.presentation

import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel

sealed class WeatherEvent {
    data class CurrentWeatherReceived(val currentWeatherModel: CurrentWeatherModel) : WeatherEvent()
    data object CurrentWeatherLoading : WeatherEvent()
    data class CurrentWeatherError(val message: String) : WeatherEvent()
    /*data class ForecastDataReceived(val forecastModel: ForecastModel) : WeatherEvent()
    data object ForecastDataLoading : WeatherEvent()
    data class ForecastDataError(val message: String) : WeatherEvent()*/
}