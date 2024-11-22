package com.example.composelocationweather.location.data.repository

import com.example.composelocationweather.feature_location.data.LocationDao
import com.example.composelocationweather.feature_location.data.repository.LocationRepositoryImpl
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock

class LocationRepositoryShould {

    private val locationDao: LocationDao = mock()
    private val repository = LocationRepositoryImpl(locationDao)
    private val location: UserLocation = mock()

    @Test
    fun callSaveLocationFromLocationDao() = runTest {
        repository.saveLocation(location)

        verify(locationDao, times(1)).saveLocation(location)
    }

    @Test
    fun callGetLocationByIdFromLocationDao() = runTest {
        repository.getLocationById(1)

        verify(locationDao, times(1)).getLocationById(1)
    }

    @Test
    fun callGetLocationsFromLocationDao() = runTest {
        repository.getLocations()

        verify(locationDao, times(1)).getLocations()
    }

    @Test
    fun callDeleteLocationFromLocationDao() = runTest {
        repository.deleteLocation(location)

        verify(locationDao, times(1)).deleteLocation(location)
    }
}