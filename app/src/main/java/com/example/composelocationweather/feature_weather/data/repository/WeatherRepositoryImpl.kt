package com.example.composelocationweather.feature_weather.data.repository

import android.util.Log
import com.example.composelocationweather.BuildConfig
import com.example.composelocationweather.feature_weather.data.local.dao.CurrentWeatherDao
import com.example.composelocationweather.feature_weather.data.local.dao.ForecastDataDao
import com.example.composelocationweather.feature_weather.data.remote.RetrofitAPI
import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData
import com.example.composelocationweather.feature_weather.domain.model.ForecastData
import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import com.example.composelocationweather.feature_weather.domain.repository.WeatherRepository
import com.example.composelocationweather.util.ConstantData
import com.example.composelocationweather.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    private val forecastDataDao: ForecastDataDao,
    private val retrofitApi: RetrofitAPI,
) : WeatherRepository {

    override suspend fun getLocalWeatherData(): CurrentWeatherData {
        return currentWeatherDao.getWeatherData()
    }

    override suspend fun getLocalForecastData(): ForecastData {
        return forecastDataDao.getForecastData()
    }

    override suspend fun getNetworkWeatherData(weatherRequestData: WeatherRequestData): Resource<CurrentWeatherModel> {
        return try {
            val response = retrofitApi.getCurrentWeather(
                latitude = weatherRequestData.latitude,
                longitude = weatherRequestData.longitude,
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    currentWeatherDao.insert(CurrentWeatherData(it))
                    return@let Resource.success(it)
                } ?: Resource.error("Error, Empty forecast data", null)
            } else {
                Resource.error("Error, response was not successful: ${response.message()}", null)
            }
        } catch (e: Exception) {
            Resource.error("Error getting currentWeather data: ${e.message}", null)
        }
    }

    override suspend fun getNetworkForeCastData(weatherRequestData: WeatherRequestData): Resource<ForecastModel> {
        return try {
            val response = retrofitApi.getForeCastWeather(
                latitude = weatherRequestData.latitude,
                longitude = weatherRequestData.longitude
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    forecastDataDao.insert(ForecastData(it))
                    return@let Resource.success(it)
                } ?: Resource.error("Error, Empty forecast data", null)
            } else {
                Resource.error("Error, response was not successful: ${response.message()}", null)
            }
        } catch (e: Exception) {
            Resource.error("Error getting forecast data: ${e.message}", null)
        }
    }
}