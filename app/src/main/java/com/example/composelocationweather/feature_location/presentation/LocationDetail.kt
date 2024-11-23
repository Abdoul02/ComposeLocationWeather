package com.example.composelocationweather.feature_location.presentation

import android.annotation.SuppressLint
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelocationweather.common.component.IndeterminateCircularIndicator
import com.example.composelocationweather.feature_location.presentation.components.PlacesItem
import com.example.composelocationweather.feature_location.presentation.state.PlacesState

@Composable
fun LocationDetailScreen(
    userLocation: String,
    name: String,
    placesState: State<PlacesState>,
    getLocationInfo: (String) -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            getLocationInfo(userLocation)

            val places = placesState.value

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Restaurants near $name",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                IndeterminateCircularIndicator(isLoading = places.isLoading)
                if (places.places.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        items(places.places.take(10)) { result ->
                            PlacesItem(result = result)
                        }
                    }
                } else {
                    val message = places.errorMessage ?: "Fetching restaurants around this location"
                    Text(message)
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun LocationDetailPreview() {
    val state = mutableStateOf(PlacesState())
    LocationDetailScreen(
        userLocation = "",
        name = "Test",
        placesState = state,
        getLocationInfo = { _ -> }
    )
}