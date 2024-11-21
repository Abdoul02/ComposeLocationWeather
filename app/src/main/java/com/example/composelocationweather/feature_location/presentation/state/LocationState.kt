package com.example.composelocationweather.feature_location.presentation.state

import com.example.composelocationweather.feature_location.domain.model.UserLocation

data class LocationState(
    val userLocation: UserLocation = UserLocation(0, 0.0, 0.0, "")
)
