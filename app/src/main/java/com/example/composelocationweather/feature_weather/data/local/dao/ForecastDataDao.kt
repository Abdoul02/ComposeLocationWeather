package com.example.composelocationweather.feature_weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composelocationweather.feature_weather.domain.model.FORECAST_WEATHER_DATA_TABLE
import com.example.composelocationweather.feature_weather.domain.model.ForecastData
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastData: ForecastData)

    @Query("SELECT * FROM $FORECAST_WEATHER_DATA_TABLE")
    fun getForecastData(): ForecastData
}