package com.example.composelocationweather.feature_location.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.composelocationweather.database.LocationWeatherDB
import com.example.composelocationweather.feature_location.data.LocationDao
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@HiltAndroidTest
class LocationDaoShould {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: LocationWeatherDB
    private lateinit var dao: LocationDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.locationDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUserLocation() = runTest {
        val userLocation = UserLocation(1, 0.0, 1.1, "TestLocation")
        dao.saveLocation(userLocation)

        val locationList = dao.getLocations().first()
        assertThat(locationList).contains(userLocation)
    }

    @Test
    fun deleteUserLocation() = runTest {
        val userLocation = UserLocation(1, 0.0, 1.1, "TestLocation")
        dao.saveLocation(userLocation)
        dao.deleteLocation(userLocation)

        val locationList = dao.getLocations().first()
        assertThat(locationList).doesNotContain(userLocation)
    }

    @Test
    fun getUserLocationById() = runTest {
        val userLocation = UserLocation(1, 0.0, 1.1, "TestLocation")
        dao.saveLocation(userLocation)


        val location = dao.getLocationById(1)
        assertThat(location).isNotNull()
        assertThat(location).isEqualTo(userLocation)
    }

    @Test
    fun getUserLocations() = runTest {
        val userLocation = UserLocation(1, 0.0, 1.1, "TestLocation")
        dao.saveLocation(userLocation)


        val locationList = dao.getLocations().first()
        assertThat(locationList).isNotEmpty()
    }


}