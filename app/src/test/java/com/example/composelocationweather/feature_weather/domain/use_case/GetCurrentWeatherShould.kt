package com.example.composelocationweather.feature_weather.domain.use_case

import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData
import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.repository.WeatherRepository
import com.example.composelocationweather.util.Utils
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock

class GetCurrentWeatherShould {

    private val repository: WeatherRepository = mock()

    //val context  =  Mockito.mock(Context::class.java)
    //private val util: Utils = Mockito.spy(Utils(context))
    private val util: Utils = mock()
    private val weatherRequestData: WeatherRequestData = mock()


    @Test
    fun getLocalWeatherDataWhenDeviceIsOffline() = runTest {
        whenever(util.isDeviceOnline()).thenReturn(false)
        whenever(repository.getLocalWeatherData()).thenReturn(CurrentWeatherData(CurrentWeatherModel()))
        val useCase = GetCurrentWeather(repository, util)
        useCase.invoke(weatherRequestData)

        verify(repository, times(1)).getLocalWeatherData()
    }

    @Test
    fun getNetworkWeatherDataWhenDeviceIsOnline() = runTest {
        whenever(util.isDeviceOnline()).thenReturn(true)
        val useCase = GetCurrentWeather(repository, util)
        useCase.invoke(weatherRequestData)

        verify(repository, times(1)).getNetworkWeatherData(weatherRequestData)
    }

}