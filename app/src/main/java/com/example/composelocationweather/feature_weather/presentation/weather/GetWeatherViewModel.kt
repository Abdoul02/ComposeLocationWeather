package com.example.composelocationweather.feature_weather.presentation.weather

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.composelocationweather.feature_weather.domain.model.WeatherRequestData
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.use_case.WeatherUseCase
import com.example.composelocationweather.location.AppLocationProvider
import com.example.composelocationweather.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetWeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val locationProvider: AppLocationProvider
) : ViewModel() {

    private val _currentWeatherState = mutableStateOf(CurrentWeatherState(CurrentWeatherModel()))
    val currentWeatherState: State<CurrentWeatherState> = _currentWeatherState

    fun getCurrentWeather() {
        viewModelScope.launch {
            locationProvider.currentLocation.asFlow().collect { currentLocation ->
                currentLocation?.let { location ->
                    val request = WeatherRequestData(
                        location.latitude.toString(),
                        location.longitude.toString()
                    )

                    weatherUseCase.getCurrentWeather(request)
                        .also { currentWeatherResponse ->
                            when (currentWeatherResponse.status) {
                                Status.SUCCESS -> {
                                    currentWeatherResponse.data?.let { currentWeather ->
                                        _currentWeatherState.value = currentWeatherState.value.copy(
                                            currentWeatherData = currentWeather
                                        )
                                    }
                                }

                                Status.ERROR -> {
                                    Log.e(
                                        "GetWeatherViewModel",
                                        "Error ${currentWeatherResponse.message}"
                                    )
                                }

                                Status.LOADING -> {
                                    Log.e("GetWeatherViewModel", "Loading....")
                                }
                            }
                        }
                }
            }
        }
    }

    init {
        //locationProvider.currentLocation.
        //val weatherRequestData = WeatherRequestData(latitude = "", longitude = "")
        getCurrentWeather()
    }
}