package com.example.composelocationweather.feature_location.presentation.state

import com.example.composelocationweather.feature_location.domain.model.UserLocation

data class LocationListState(
    val locations: List<UserLocation> = emptyList()
)
