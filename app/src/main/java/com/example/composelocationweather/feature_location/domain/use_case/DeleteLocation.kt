package com.example.composelocationweather.feature_location.domain.use_case

import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.repository.LocationRepository

class DeleteLocation(private val repository: LocationRepository) {
    suspend operator fun invoke(location: UserLocation) {
        repository.deleteLocation(location)
    }
}