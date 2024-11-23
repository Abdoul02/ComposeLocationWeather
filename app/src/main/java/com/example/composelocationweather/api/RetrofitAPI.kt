package com.example.composelocationweather.api

import com.example.composelocationweather.BuildConfig
import com.example.composelocationweather.feature_location.domain.model.places.PlacesResponse
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import com.example.composelocationweather.util.ConstantData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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

    @GET
    suspend fun getLocationInfo(
        @Url url: String = ConstantData.PLACE_URL,
        @Query("location") location: String,
        @Query("key") key: String = BuildConfig.PLACES_API_KEY,
        @Query("type") type: String = ConstantData.PLACE_TYPE,
        @Query("radius") radius: Int = ConstantData.RADIUS,
    ): Response<PlacesResponse>
}