package com.example.composelocationweather.feature_weather.domain.use_case

import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import com.example.composelocationweather.feature_weather.domain.repository.WeatherRepository
import com.example.composelocationweather.util.Resource
import com.example.composelocationweather.util.Utils

class GetForecastData(
    private val weatherRepository: WeatherRepository,
    private val utils: Utils
) {
    suspend operator fun invoke(weatherRequestData: WeatherRequestData): Resource<ForecastModel> {
        return if (!utils.isDeviceOnline()) {
            Resource.success(weatherRepository.getLocalForecastData().forecastModel)
        } else {
            weatherRepository.getNetworkForeCastData(weatherRequestData)
        }
    }
}