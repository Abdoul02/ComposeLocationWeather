package com.example.composelocationweather.feature_weather.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.composelocationweather.DummyData
import com.example.composelocationweather.feature_weather.presentation.state.CurrentWeatherState
import com.example.composelocationweather.feature_weather.presentation.state.ForecastDataState
import com.example.composelocationweather.util.TestTags
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private val currentWeatherState =
        mutableStateOf(CurrentWeatherState(DummyData.dummyCurrentWeather, null, false))
    private val forecastWeatherState =
        mutableStateOf(
            ForecastDataState(
                forecastDetails = DummyData.dummyForecastDetails,
                null,
                false
            )
        )


    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            WeatherInfoScreen(
                navController = navController,
                currentWeatherState = currentWeatherState,
                forecastDataState = forecastWeatherState,
                isDeviceOnline = true
            ) {

            }
        }
    }

    @Test
    fun verifyAllViewsAreDisplayed() {
        composeTestRule.onNodeWithTag(TestTags.CURRENT_TEMP_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.CURRENT_TEMP_DESC).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.FORECAST_LIST).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.LOCATION_NAV_FAB).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.CURRENT_MIN_TEMP_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.CURRENT_MAX_TEMP_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.CURRENT_TEMP_INFO_TEXT).assertIsDisplayed()
    }

    @Test
    fun verifyLastSyncIsNotDisplayed() {
        composeTestRule.onNodeWithTag(TestTags.LAST_SYNC_TIME_TEXT).assertIsNotDisplayed()
    }

    @Test
    fun saveLocation() = runTest {
        composeTestRule.onNodeWithTag(TestTags.LOCATION_NAV_FAB).performClick()
        composeTestRule.onNodeWithTag(TestTags.SAVE_LOCATION_TEXT).performClick()
        composeTestRule.onNodeWithTag(TestTags.WEATHER_SCREEN_SNACK_BAR).assertIsDisplayed()
    }
}