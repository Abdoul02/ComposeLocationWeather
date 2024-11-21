package com.example.composelocationweather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.composelocationweather.feature_location.data.LocationDao
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_weather.data.local.dao.CurrentWeatherDao
import com.example.composelocationweather.feature_weather.data.local.dao.ForecastDataDao
import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData
import com.example.composelocationweather.feature_weather.domain.model.ForecastData

@Database(
    entities = [CurrentWeatherData::class, ForecastData::class, UserLocation::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class LocationWeatherDB : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun forecastDataDao(): ForecastDataDao
    abstract fun locationDao(): LocationDao
}