package com.example.composelocationweather.location.data.repository

import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.model.places.PlacesResponse
import com.example.composelocationweather.feature_location.domain.repository.LocationRepository
import com.example.composelocationweather.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocationRepository : LocationRepository {
    private val locations = mutableListOf<UserLocation>()
    private val placesResponse = PlacesResponse(
        html_attributions = emptyList(),
        status = "",
        results = emptyList(),
        next_page_token = ""
    )

    override fun getLocations(): Flow<List<UserLocation>> = flow {
        emit(locations)
    }

    override suspend fun getLocationById(id: Int): UserLocation? {
        return locations.firstOrNull { it.id == id }
    }

    override suspend fun saveLocation(location: UserLocation) {
        locations.add(location)
    }

    override suspend fun deleteLocation(location: UserLocation) {
        locations.remove(location)
    }

    override suspend fun getLocationInformation(
        location: String
    ): Resource<PlacesResponse> {
        return Resource.success(placesResponse)
    }
}