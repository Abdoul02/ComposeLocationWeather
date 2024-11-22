package com.example.composelocationweather.feature_location.presentation

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import com.example.composelocationweather.feature_location.presentation.state.LocationListState
import com.example.composelocationweather.util.toDate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapsScreen(
    locationListState: State<LocationListState>,
    currentLocation: LiveData<Location?>
) {

    val locationList = locationListState.value.locations

    val cameraState = rememberCameraPositionState()
    val userLocation = currentLocation.observeAsState()

    userLocation.value?.let { location ->
        LaunchedEffect(key1 = currentLocation) {
            cameraState.centerOnLocation(
                LatLng(
                    location.latitude,
                    location.longitude
                )
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(
            isMyLocationEnabled = true,
            mapType = MapType.NORMAL,
            isTrafficEnabled = true
        )

    ) {
        if (locationList.isNotEmpty()) {
            locationList.forEach { location ->
                Marker(
                    state = MarkerState(LatLng(location.latitude, location.longitude)),
                    title = location.name,
                    snippet = "Saved on: ${location.dateCreated.toDate()}"
                )
            }
        }

        userLocation.value?.let { userLocation ->
            Marker(
                state = MarkerState(LatLng(userLocation.latitude, userLocation.longitude)),
                title = "I am here",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        11f
    ),
    durationMs = 2000
)