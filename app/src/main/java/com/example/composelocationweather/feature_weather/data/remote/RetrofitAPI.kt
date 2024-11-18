package com.example.composelocationweather.feature_weather.data.remote

import com.example.composelocationweather.BuildConfig
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") metric: String = "metric",
        @Query("appid") key: String = BuildConfig.WEATHER_API_KEY
    ): Response<CurrentWeatherModel>

    @GET("/data/2.5/forecast")
    suspend fun getForeCastWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") metric: String = "metric",
        @Query("appid") key: String = BuildConfig.WEATHER_API_KEY
    ): Response<ForecastModel>
}