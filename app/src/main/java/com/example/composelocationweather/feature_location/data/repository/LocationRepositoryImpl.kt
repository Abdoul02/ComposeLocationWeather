package com.example.composelocationweather.feature_location.data.repository

import com.example.composelocationweather.feature_location.data.LocationDao
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {
    override fun getLocations(): Flow<List<UserLocation>> {
        return locationDao.getLocations()
    }

    override suspend fun getLocationById(id: Int): UserLocation? {
        return locationDao.getLocationById(id)
    }

    override suspend fun saveLocation(location: UserLocation) {
        locationDao.saveLocation(location)
    }

    override suspend fun deleteLocation(location: UserLocation) {
        locationDao.deleteLocation(location)
    }
}