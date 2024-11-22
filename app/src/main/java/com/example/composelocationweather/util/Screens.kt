package com.example.composelocationweather.util

import kotlinx.serialization.Serializable

object Screens {

    @Serializable
    object WeatherScreen

    @Serializable
    object LocationScreen

    @Serializable
    object MapScreen

    @Serializable
    data class LocationDetail(val id: Int)
}