package com.example.composelocationweather.location.domain.use_case

import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.use_case.DeleteLocation
import com.example.composelocationweather.feature_location.domain.use_case.GetLocationById
import com.example.composelocationweather.feature_location.domain.use_case.GetLocations
import com.example.composelocationweather.feature_location.domain.use_case.SaveLocation
import com.example.composelocationweather.location.data.repository.FakeLocationRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationUseCaseShould {

    private val repository = FakeLocationRepository()
    private val fakeUserLocation = UserLocation(1, 0.0, 0.0, "Anywhere")

    @Test
    fun saveUserLocation() = runTest {
        val saveLocation = SaveLocation(repository)
        saveLocation.invoke(fakeUserLocation)

        assertTrue(repository.getLocations().first().contains(fakeUserLocation))
    }

    @Test
    fun getUserLocationById() = runTest {
        val getLocationById = GetLocationById(repository)
        repository.saveLocation(fakeUserLocation)

        val actualLocation = getLocationById.invoke(fakeUserLocation.id)

        assertEquals(actualLocation, fakeUserLocation)
    }

    @Test
    fun deleteUserLocation() = runTest {
        val deleteLocation = DeleteLocation(repository)
        repository.saveLocation(fakeUserLocation)

        deleteLocation.invoke(fakeUserLocation)

        assertFalse(repository.getLocations().first().contains(fakeUserLocation))
    }

    @Test
    fun getAllLocation() = runTest {
        val getLocations = GetLocations(repository)
        repository.saveLocation(fakeUserLocation)

        val allLocations = getLocations.invoke()

        assertTrue(allLocations.first().isNotEmpty())
    }
}