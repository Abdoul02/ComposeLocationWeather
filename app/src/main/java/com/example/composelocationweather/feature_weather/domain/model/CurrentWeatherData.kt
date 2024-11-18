package com.example.composelocationweather.feature_weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel


const val CURRENT_WEATHER_DATA_TABLE = "current_weather_data"
const val CURRENT_WEATHER_DATA_ID = 1

@Entity(tableName = CURRENT_WEATHER_DATA_TABLE)
data class CurrentWeatherData(val currentWeatherModel: CurrentWeatherModel) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_DATA_ID
}
