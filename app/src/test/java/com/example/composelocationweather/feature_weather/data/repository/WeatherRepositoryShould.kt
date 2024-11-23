package com.example.composelocationweather.feature_weather.data.repository

import com.example.composelocationweather.api.RetrofitAPI
import com.example.composelocationweather.feature_weather.data.local.dao.CurrentWeatherDao
import com.example.composelocationweather.feature_weather.data.local.dao.ForecastDataDao
import com.example.composelocationweather.feature_weather.domain.model.CurrentWeatherData
import com.example.composelocationweather.feature_weather.domain.model.ForecastData
import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastModel
import com.example.composelocationweather.util.Resource
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response


class WeatherRepositoryShould {

    private val currentWeatherDao: CurrentWeatherDao = mock()
    private val forecastDataDao: ForecastDataDao = mock()
    private val retrofitApi: RetrofitAPI = mock()
    private val weatherRequestData: WeatherRequestData = mock()

    private val currentWeatherData: CurrentWeatherData = mock()
    private val forecastData: ForecastData = mock()
    private val currentWeatherModel: CurrentWeatherModel = mock()
    private val forecastModel: ForecastModel = mock()

    @Test
    fun callGetWeatherDataFromCurrentWeatherDao() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)

        repository.getLocalWeatherData()

        verify(currentWeatherDao, times(1)).getWeatherData()
    }

    @Test
    fun getWeatherData() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)
        whenever(currentWeatherDao.getWeatherData()).thenReturn(currentWeatherData)

        val response = repository.getLocalWeatherData()

        assertEquals(response, currentWeatherData)
    }

    @Test
    fun callGetForecastDataFromForecastDataDao() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)

        repository.getLocalForecastData()

        verify(forecastDataDao, times(1)).getForecastData()
    }

    @Test
    fun getForecastData() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)
        whenever(forecastDataDao.getForecastData()).thenReturn(forecastData)

        val response = repository.getLocalForecastData()

        assertEquals(response, forecastData)
    }

    @Test
    fun callGetCurrentWeatherFromRetrofit() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)

        repository.getNetworkWeatherData(weatherRequestData)

        verify(retrofitApi, times(1)).getCurrentWeather(
            weatherRequestData.latitude,
            weatherRequestData.longitude
        )
    }

    @Test
    fun successfullyGetWeatherDataFromApi() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)
        whenever(
            retrofitApi.getCurrentWeather(
                weatherRequestData.latitude,
                weatherRequestData.longitude
            )
        )
            .thenReturn(Response.success(currentWeatherModel))

        val response = repository.getNetworkWeatherData(weatherRequestData)

        assertEquals(response, Resource.success(currentWeatherModel))
    }

    @Test
    fun returnErrorWhenErrorOccursFromApi() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)
        val errorResponse =
            "{\n" +
                    "  \"type\": \"error\",\n" +
                    "  \"message\": \"backend :-D.\"\n" + "}"

        whenever(
            retrofitApi.getCurrentWeather(
                weatherRequestData.latitude,
                weatherRequestData.longitude
            )
        )
            .thenReturn(Response.error(400, errorResponse.toResponseBody()))

        val response = repository.getNetworkWeatherData(weatherRequestData)

        assertEquals(
            response,
            Resource.error("Error, couldn't get weather data", null)
        )
    }

    @Test
    fun callGetForeCastWeatherFromRetrofit() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)

        repository.getNetworkForeCastData(weatherRequestData)

        verify(retrofitApi, times(1)).getForeCastWeather(
            weatherRequestData.latitude,
            weatherRequestData.longitude
        )
    }

    @Test
    fun successfullyGetForeCastWeatherFromApi() = runTest {
        val repository = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi)
        whenever(
            retrofitApi.getForeCastWeather(
                weatherRequestData.latitude,
                weatherRequestData.longitude
            )
        )
            .thenReturn(Response.success(forecastModel))

        val response = repository.getNetworkForeCastData(weatherRequestData)

        assertEquals(response, Resource.success(forecastModel))
    }
}