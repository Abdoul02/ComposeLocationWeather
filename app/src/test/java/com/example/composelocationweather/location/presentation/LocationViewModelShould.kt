package com.example.composelocationweather.location.presentation

import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.domain.use_case.DeleteLocation
import com.example.composelocationweather.feature_location.domain.use_case.GetLocationById
import com.example.composelocationweather.feature_location.domain.use_case.GetLocations
import com.example.composelocationweather.feature_location.domain.use_case.LocationUseCases
import com.example.composelocationweather.feature_location.domain.use_case.SaveLocation
import com.example.composelocationweather.feature_location.presentation.LocationViewModel
import com.example.composelocationweather.util.BaseUnitTest
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class LocationViewModelShould : BaseUnitTest() {

    private val locationUseCases: LocationUseCases = mock()
    private val fakeUserLocation = UserLocation(1, 0.0, 0.0, "Anywhere")
    private val saveLocation: SaveLocation = mock()
    private val deleteLocation: DeleteLocation = mock()
    private val viewModel = LocationViewModel(locationUseCases)
    private val getLocations: GetLocations = mock()

    @Test
    fun callSaveLocationFromLocationUseCases() = runTest(UnconfinedTestDispatcher()) {
        whenever(locationUseCases.saveLocation).thenReturn(saveLocation)
        viewModel.saveUserLocation(fakeUserLocation)

        verify(locationUseCases.saveLocation, times(1)).invoke(fakeUserLocation)
    }

    @Test
    fun callDeleteLocationFromLocationUseCases() = runTest(UnconfinedTestDispatcher()) {
        whenever(locationUseCases.deleteLocation).thenReturn(deleteLocation)
        viewModel.deleteUserLocation(fakeUserLocation)

        verify(locationUseCases.deleteLocation, times(1)).invoke(fakeUserLocation)
    }

/*    @Test
    fun callGetLocationsFromLocationUseCases() = runTest(UnconfinedTestDispatcher()) {
        whenever(locationUseCases.getLocations).thenReturn(getLocations)
        viewModel.getAllSavedLocation()

        verify(locationUseCases.getLocations, times(1)).invoke()
    }*/
}