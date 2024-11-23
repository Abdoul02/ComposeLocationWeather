package com.example.composelocationweather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composelocationweather.feature_location.presentation.LocationScreen
import com.example.composelocationweather.feature_location.presentation.LocationViewModel
import com.example.composelocationweather.feature_location.presentation.MapsScreen
import com.example.composelocationweather.feature_weather.presentation.GetWeatherViewModel
import com.example.composelocationweather.feature_weather.presentation.WeatherInfoScreen
import com.example.composelocationweather.location.AppLocationProvider
import com.example.composelocationweather.ui.theme.ComposeLocationWeatherTheme
import com.example.composelocationweather.util.Screens
import com.example.composelocationweather.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appLocationProvider: AppLocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ComposeLocationWeatherTheme {
                MainApp()
            }
        }
    }

    @Composable
    fun MainApp() {
        val getWeatherViewModel: GetWeatherViewModel = hiltViewModel()
        val locationViewModel: LocationViewModel = hiltViewModel()

        val utils = Utils(this.applicationContext)
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        var shouldDirectUserToApplicationSettings by remember {
            mutableStateOf(false)
        }
        var shouldShowPermissionRationale by remember {
            mutableStateOf(
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        var arePermissionGranted by remember {
            mutableStateOf(
                areLocationPermissionsAlreadyGranted()
            )
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),

            ) { permissions ->
            val permissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted
            }
            if (!permissionsGranted) {
                shouldShowPermissionRationale =
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            shouldDirectUserToApplicationSettings =
                !shouldShowPermissionRationale && !areLocationPermissionsAlreadyGranted()

            arePermissionGranted = areLocationPermissionsAlreadyGranted()
            if (areLocationPermissionsAlreadyGranted()) {
                if (appLocationProvider.isLocationEnabled()) {
                    getWeatherViewModel.getCurrentWeather()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Please enable location to continue")
                    }
                    gotoLocationSetting()
                }
            }
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(key1 = lifecycleOwner, effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START &&
                    !areLocationPermissionsAlreadyGranted() &&
                    !shouldShowPermissionRationale
                ) {
                    permissionLauncher.launch(locationPermissions)
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
        )
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (arePermissionGranted) {
                        if (appLocationProvider.isLocationEnabled()) {
                            val navController = rememberNavController()
                            NavHost(
                                navController = navController,
                                startDestination = Screens.WeatherScreen
                            ) {
                                composable<Screens.WeatherScreen> {
                                    WeatherInfoScreen(
                                        navController,
                                        getWeatherViewModel.currentWeatherState,
                                        getWeatherViewModel.forecastState,
                                        utils.isDeviceOnline(),
                                    ) { location ->
                                        locationViewModel.saveUserLocation(location)
                                    }
                                }

                                composable<Screens.LocationScreen> {
                                    LocationScreen(
                                        locationListState = locationViewModel.locationStateFlow.collectAsStateWithLifecycle()
                                    ) { location ->
                                        locationViewModel.deleteUserLocation(location)
                                    }
                                }

                                composable<Screens.MapScreen> {
                                    MapsScreen(
                                        locationListState = locationViewModel.locationStateFlow.collectAsStateWithLifecycle(),
                                        currentLocation = appLocationProvider.currentLocation
                                    )
                                }
                            }
                        } else {

                            Text(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxWidth(),
                                text = "Please enable location and click refresh",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = {
                                    if (appLocationProvider.isLocationEnabled()) {
                                        getWeatherViewModel.getCurrentWeather()
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Please enable location to continue")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Get current weather",
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    } else {
                        Text(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxWidth(),
                            text = "Location permission required",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (shouldShowPermissionRationale) {
                    LaunchedEffect(Unit) {
                        scope.launch {
                            val userAction = snackbarHostState.showSnackbar(
                                message = "Please authorize location permissions",
                                actionLabel = "Approve",
                                duration = SnackbarDuration.Indefinite,
                                withDismissAction = true
                            )
                            when (userAction) {
                                SnackbarResult.ActionPerformed -> {
                                    shouldShowPermissionRationale = false
                                    permissionLauncher.launch(locationPermissions)
                                }

                                SnackbarResult.Dismissed -> {
                                    shouldShowPermissionRationale = false
                                }
                            }
                        }
                    }
                }

                if (shouldDirectUserToApplicationSettings) {
                    openApplicationSettings()
                }
            }

        }
    }

    private fun gotoLocationSetting() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    private fun areLocationPermissionsAlreadyGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openApplicationSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also {
            startActivity(it)
        }
    }
}