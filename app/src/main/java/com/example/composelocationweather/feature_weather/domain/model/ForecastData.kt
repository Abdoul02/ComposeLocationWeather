package com.example.composelocationweather.feature_weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel

const val FORECAST_WEATHER_DATA_TABLE = "forecast_weather_data"
const val FORECAST_WEATHER_DATA_ID = 1

@Entity(tableName = FORECAST_WEATHER_DATA_TABLE)
data class ForecastData(val forecastModel: ForecastModel) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = FORECAST_WEATHER_DATA_ID
}
