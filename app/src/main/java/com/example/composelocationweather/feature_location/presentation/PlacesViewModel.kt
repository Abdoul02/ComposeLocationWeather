package com.example.composelocationweather.feature_location.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composelocationweather.feature_location.domain.use_case.LocationUseCases
import com.example.composelocationweather.feature_location.presentation.state.PlacesState
import com.example.composelocationweather.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val locationUseCases: LocationUseCases) :
    ViewModel() {

    private val _placesState = mutableStateOf(PlacesState())
    val placesState: State<PlacesState> = _placesState

    fun getLocationInformation(location: String) {
        viewModelScope.launch {
            locationUseCases.getLocationInformation( location).also { placesResponse ->
                when (placesResponse.status) {
                    Status.SUCCESS -> {
                        placesResponse.data?.let { places ->
                            _placesState.value = placesState.value.copy(
                                places = places.results,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    Status.ERROR -> {
                        _placesState.value = placesState.value.copy(
                            places = emptyList(),
                            isLoading = false,
                            errorMessage = placesResponse.message
                        )
                    }

                    Status.LOADING -> {
                        _placesState.value = placesState.value.copy(
                            places = emptyList(),
                            isLoading = true,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }
}