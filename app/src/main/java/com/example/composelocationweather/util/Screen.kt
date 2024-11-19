package com.example.composelocationweather.util

sealed class Screen(val route: String) {
    object WeatherScreen : Screen("weather_screen")
    object LocationScreen : Screen("add_edit_note_screen")
}
