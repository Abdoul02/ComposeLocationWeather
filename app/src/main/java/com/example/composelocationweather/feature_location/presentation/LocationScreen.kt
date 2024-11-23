package com.example.composelocationweather.feature_location.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composelocationweather.feature_location.domain.model.UserLocation
import com.example.composelocationweather.feature_location.presentation.components.LocationItem
import com.example.composelocationweather.feature_location.presentation.state.LocationListState
import com.example.composelocationweather.util.Screens
import kotlinx.coroutines.launch

@Composable
fun LocationScreen(
    navController: NavController,
    locationListState: State<LocationListState>,
    onLocationDelete: (UserLocation) -> Unit
) {
    val locations = locationListState.value.locations
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            if (locations.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 5.dp)
                ) {
                    Text(
                        text = "Favorite Locations",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        items(locations) { location ->
                            LocationItem(
                                location = location,
                                onDeleteClick = {

                                    onLocationDelete(location)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Location deleted!")
                                    }
                                },
                                onItemClick = {
                                    navController.navigate(
                                        Screens.LocationDetail(
                                            "${location.latitude},${location.longitude}",
                                            location.name
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("You currently do not have any location saved.")
                }
            }
        }
    }
}