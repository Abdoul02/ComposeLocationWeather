package com.example.composelocationweather.feature_weather.data.repository

import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData
import com.example.composelocationweather.feature_weather.domain.model.ForecastData
import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import com.example.composelocationweather.feature_weather.domain.repository.WeatherRepository
import com.example.composelocationweather.util.Resource

class FakeRepository : WeatherRepository {

    private val currentWeatherModel = CurrentWeatherModel()
    private val forecastModel = ForecastModel()
    override suspend fun getLocalWeatherData(): CurrentWeatherData {
        return CurrentWeatherData(currentWeatherModel)
    }

    override suspend fun getLocalForecastData(): ForecastData {
        return ForecastData(forecastModel)
    }

    override suspend fun getNetworkWeatherData(weatherRequestData: WeatherRequestData): Resource<CurrentWeatherModel> {
        return Resource.success(currentWeatherModel)
    }

    override suspend fun getNetworkForeCastData(weatherRequestData: WeatherRequestData): Resource<ForecastModel> {
        return Resource.success(forecastModel)
    }
}