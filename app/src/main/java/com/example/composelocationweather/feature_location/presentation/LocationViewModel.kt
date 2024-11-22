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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases
) : ViewModel() {

    private val _locationState = mutableStateOf(LocationState())
    val locationState: State<LocationState> = _locationState

    private val _locationStateFlow = MutableStateFlow(LocationListState())
    val locationStateFlow: StateFlow<LocationListState> = _locationStateFlow
        .onStart { getAllSavedLocation() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            LocationListState()
        )

    fun saveUserLocation(location: UserLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            locationUseCases.saveLocation(location)
        }
    }

    fun deleteUserLocation(location: UserLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            locationUseCases.deleteLocation(location)
        }
    }

    fun getLocationById(id: Int) {
    }

    fun getAllSavedLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            locationUseCases.getLocations()
                .onEach { userLocations ->
                    _locationStateFlow.value = locationStateFlow.value.copy(
                        locations = userLocations
                    )
                }.launchIn(this)
        }
    }
}