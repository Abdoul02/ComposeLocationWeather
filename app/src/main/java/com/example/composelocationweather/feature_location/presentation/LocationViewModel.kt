package com.example.composelocationweather.feature_location.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.use_case.LocationUseCases
import com.example.composelocationweather.feature_location.presentation.state.LocationListState
import com.example.composelocationweather.feature_location.presentation.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases
) : ViewModel() {

    private val _locationState = mutableStateOf(LocationState())
    val locationState: State<LocationState> = _locationState

    private val _locationListState = mutableStateOf(LocationListState())
    val locationListState: State<LocationListState> = _locationListState

    private var getLocationsJob: Job? = null

    fun saveUserLocation(location: UserLocation) {
        Log.d("SaveLocation", "Location: $location")
        viewModelScope.launch(Dispatchers.IO) {
            locationUseCases.saveLocation(location)
        }
    }

    init {
        getAllLocations()
    }

    fun deleteUserLocation(location: UserLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            locationUseCases.deleteLocation(location)
        }
    }

    fun getLocationById(id: Int) {
    }

    private fun getAllLocations() {
        getLocationsJob?.cancel()
        getLocationsJob = locationUseCases.getLocations()
            .onEach { locations ->
                _locationListState.value = locationListState.value.copy(
                    locations = locations
                )
            }.launchIn(viewModelScope)
    }
}