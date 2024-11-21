package com.example.composelocationweather.feature_location.domain.use_case

import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocations(private val repository: LocationRepository) {

    operator fun invoke(): Flow<List<UserLocation>> {
        return repository.getLocations()
    }
}