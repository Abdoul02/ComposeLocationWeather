package com.example.composelocationweather.feature_weather.domain.use_case

import com.example.composelocationweather.feature_weather.domain.model.ForecastData
import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import com.example.composelocationweather.feature_weather.domain.repository.WeatherRepository
import com.example.composelocationweather.util.Resource
import com.example.composelocationweather.util.Utils
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class GetForecastDataShould {

    private val repository: WeatherRepository = mock()
    private val util: Utils = mock()
    private val weatherRequestData: WeatherRequestData = mock()

    @Test
    fun callGetLocalForecastDataWhenDeviceIsOffline() = runTest {
        whenever(util.isDeviceOnline()).thenReturn(false)
        whenever(repository.getLocalForecastData()).thenReturn(ForecastData(ForecastModel()))
        val useCase = GetForecastData(repository, util)
        useCase.invoke(weatherRequestData)

        verify(repository, times(1)).getLocalForecastData()
    }

    @Test
    fun returnForecastModelResource() = runTest {
        whenever(repository.getLocalForecastData()).thenReturn(ForecastData(ForecastModel()))
        val useCase = GetForecastData(repository, util)
        val response = useCase.invoke(weatherRequestData)

        assertEquals(response, Resource.success(ForecastModel()))
    }

    @Test
    fun callGetNetworkForecastDataWhenDeviceIsOnline() = runTest {
        whenever(util.isDeviceOnline()).thenReturn(true)
        val useCase = GetForecastData(repository, util)
        useCase.invoke(weatherRequestData)

        verify(repository, times(1)).getNetworkForeCastData(weatherRequestData)
    }
}