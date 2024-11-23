package com.example.composelocationweather.feature_location.data.repository

import com.example.composelocationweather.api.RetrofitAPI
import com.example.composelocationweather.feature_location.data.LocationDao
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.model.places.PlacesResponse
import com.example.composelocationweather.feature_location.domain.repository.LocationRepository
import com.example.composelocationweather.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val retrofitApi: RetrofitAPI
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

    override suspend fun getLocationInformation(
        location: String
    ): Resource<PlacesResponse> {
        return try {
            val response = retrofitApi.getLocationInfo(
                location = location
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error, No data regarding this place", null)
            } else {
                Resource.error("Error, response was not successful: ${response.message()}", null)
            }
        } catch (e: Exception) {
            Resource.error("Error getting data: ${e.message}", null)
        }
    }
}