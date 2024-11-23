package com.example.composelocationweather.feature_location.domain.use_case

import com.example.composelocationweather.feature_location.domain.model.places.PlacesResponse
import com.example.composelocationweather.feature_location.domain.repository.LocationRepository
import com.example.composelocationweather.util.Resource

class GetLocationInformation(private val repository: LocationRepository) {

    suspend operator fun invoke(location: String): Resource<PlacesResponse> {
        return repository.getLocationInformation(location)
    }
}