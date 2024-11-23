package com.example.composelocationweather.feature_location.presentation.state

import com.example.composelocationweather.feature_location.domain.model.places.Result

data class PlacesState(
    val places: List<Result> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = true
)
