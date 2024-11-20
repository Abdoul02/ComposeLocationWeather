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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composelocationweather.feature_weather.presentation.GetWeatherViewModel
import com.example.composelocationweather.feature_weather.presentation.WeatherInfoScreen
import com.example.composelocationweather.ui.theme.ComposeLocationWeatherTheme
import com.example.composelocationweather.util.Screen
import com.example.composelocationweather.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val getWeatherViewModel: GetWeatherViewModel = hiltViewModel()
            val utils = Utils(this.applicationContext)
            val locationPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            val locationPermissionsGranted by remember {
                mutableStateOf(areLocationPermissionsAlreadyGranted())
            }
            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }
            var shouldShowPermissionRationale by remember {
                mutableStateOf(
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }

            var currentPermissionsStatus by remember {
                mutableStateOf(
                    decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
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
                    !shouldShowPermissionRationale && !locationPermissionsGranted
                currentPermissionsStatus = decideCurrentPermissionStatus(
                    locationPermissionsGranted,
                    shouldShowPermissionRationale
                )
            }

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START &&
                        !locationPermissionsGranted &&
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

            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }

            ComposeLocationWeatherTheme {
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

                            if (areLocationPermissionsAlreadyGranted()) {

                                val navController = rememberNavController()
                                NavHost(
                                    navController = navController,
                                    startDestination = Screen.WeatherScreen.route
                                ) {
                                    composable(route = Screen.WeatherScreen.route) {
                                        WeatherInfoScreen(
                                            getWeatherViewModel.currentWeatherState,
                                            getWeatherViewModel.forecastState,
                                            utils.isDeviceOnline()
                                        )
                                    }
                                }

                                /*val currentWeatherState = getWeatherViewModel.currentWeatherState.value
                                Text(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .fillMaxWidth(),
                                    text = "Current Weather: ${currentWeatherState.currentWeatherData?.main?.temp}C",
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.padding(20.dp))*/
                            } else {
                                Text(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .fillMaxWidth(),
                                    text = "Current Permission Status: $currentPermissionsStatus",
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
        }
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

    private fun decideCurrentPermissionStatus(
        locationPermissionsGranted: Boolean,
        shouldShowPermissionRationale: Boolean
    ): String {
        return if (locationPermissionsGranted) "Granted"
        else if (shouldShowPermissionRationale) "Rejected"
        else "Denied"
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLocationWeatherTheme {
        Greeting("Android")
    }
}