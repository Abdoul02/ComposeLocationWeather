package com.example.composelocationweather.feature_weather.domain.model.currentWeather

import androidx.room.Embedded

data class CurrentWeatherModel(
    val base: String,
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val cod: Int,
    @Embedded(prefix = "coord_")
    val coord: Coord,
    val dt: Int,
    val id: Int,
    @Embedded(prefix = "main_")
    val main: Main,
    val name: String,
    @Embedded(prefix = "sys_")
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    val wind: Wind
) {
    constructor() : this(
        base = "",
        clouds = Clouds(all = 0),
        cod = 0,
        coord = Coord(0.0, 0.0),
        dt = 0,
        id = 1,
        main = Main(0.0, 1, 1, 0.0, 0.0, 0.0),
        name = "",
        sys = Sys("", 1, 1, 1, 1),
        timezone = 1,
        visibility = 1,
        weather = emptyList(),
        wind = Wind(0.0)
    )
}