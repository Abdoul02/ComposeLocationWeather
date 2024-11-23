package com.example.composelocationweather.feature_location.domain.repository

import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.model.places.PlacesResponse
import com.example.composelocationweather.util.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocations(): Flow<List<UserLocation>>
    suspend fun getLocationById(id: Int): UserLocation?
    suspend fun saveLocation(location: UserLocation)
    suspend fun deleteLocation(location: UserLocation)
    suspend fun getLocationInformation(location: String): Resource<PlacesResponse>
}