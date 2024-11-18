package com.example.composelocationweather.feature_weather.domain.repository

import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData
import com.example.composelocationweather.feature_weather.domain.model.ForecastData
import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import com.example.composelocationweather.util.Resource

interface WeatherRepository {
    suspend fun getLocalWeatherData(): CurrentWeatherData
    suspend fun getLocalForecastData(): ForecastData

    suspend fun getNetworkWeatherData(weatherRequestData: WeatherRequestData): Resource<CurrentWeatherModel>
    suspend fun getNetworkForeCastData(weatherRequestData: WeatherRequestData): Resource<ForecastModel>
}