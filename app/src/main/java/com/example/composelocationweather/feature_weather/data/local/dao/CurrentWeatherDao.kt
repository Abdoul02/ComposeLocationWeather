package com.example.composelocationweather.feature_weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composelocationweather.feature_weather.domain.model.CURRENT_WEATHER_DATA_TABLE
import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentWeatherData: CurrentWeatherData)

    @Query("SELECT * FROM $CURRENT_WEATHER_DATA_TABLE")
    fun getWeatherData(): CurrentWeatherData
}