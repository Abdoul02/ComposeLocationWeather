package com.example.composelocationweather.feature_location.domain.use_case

data class LocationUseCases(
    val saveLocation: SaveLocation,
    val getLocations: GetLocations,
    val getLocationById: GetLocationById,
    val deleteLocation: DeleteLocation
)
