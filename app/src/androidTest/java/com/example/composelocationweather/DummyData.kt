package com.example.composelocationweather

import com.example.composelocationweather.feature_weather.domain.model.currentWeather.Clouds
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.Coord
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.CurrentWeatherModel
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.Main
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.Sys
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.Weather
import com.example.composelocationweather.feature_weather.domain.model.currentWeather.Wind
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastDetail
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastMain
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastWeather

object DummyData {
    val dummyCurrentWeather = CurrentWeatherModel(
        base = "stations",
        clouds = Clouds(all = 0),
        cod = 0,
        coord = Coord(-94.04, 33.44),
        dt = 1732318907,
        id = 1,
        main = Main(9.44, 1, 1, 9.44, 15.05, 9.44),
        name = "Texarkana",
        sys = Sys("", 1, 1, 1, 1),
        timezone = 1,
        visibility = 1,
        weather = listOf(
            Weather(
                id = 800,
                main = "Clear",
                description = "Clear sky",
                icon = "01n"
            )
        ),
        wind = Wind(0.0)
    )
    val dummyForecastDetails = listOf(
        ForecastDetail(
            dt = 1732320000,
            dt_txt = "2024-11-23 00:00:00",
            main = ForecastMain(
                feelLike = 25.5,
                grnd_level = 1012,
                humidity = 65,
                pressure = 1015,
                sea_level = 1013,
                temp = 27.5,
                temp_kf = 0.0,
                temp_max = 28.0,
                temp_min = 26.0
            ),
            weather = listOf(
                ForecastWeather(
                    description = "clear sky",
                    icon = "01d",
                    id = 800,
                    main = "Clear"
                )
            ),
            clouds = null,
            rain = null,
            wind = null
        ),
        ForecastDetail(
            dt = 1732406400,
            dt_txt = "2024-11-24 00:00:00",
            main = ForecastMain(
                feelLike = 20.0,
                grnd_level = 1008,
                humidity = 72,
                pressure = 1010,
                sea_level = 1011,
                temp = 21.0,
                temp_kf = 0.0,
                temp_max = 22.0,
                temp_min = 19.0
            ),
            weather = listOf(
                ForecastWeather(
                    description = "few clouds",
                    icon = "02d",
                    id = 801,
                    main = "Clouds"
                )
            ),
            clouds = null,
            rain = null,
            wind = null
        ),
        ForecastDetail(
            dt = 1732492800,
            dt_txt = "2024-11-25 00:00:00",
            main = ForecastMain(
                feelLike = 18.0,
                grnd_level = 1005,
                humidity = 80,
                pressure = 1009,
                sea_level = 1010,
                temp = 19.5,
                temp_kf = 0.0,
                temp_max = 20.0,
                temp_min = 18.0
            ),
            weather = listOf(
                ForecastWeather(
                    description = "scattered clouds",
                    icon = "03d",
                    id = 802,
                    main = "Clouds"
                )
            ),
            clouds = null,
            rain = null,
            wind = null
        ),
        ForecastDetail(
            dt = 1732579200,
            dt_txt = "2024-11-26 00:00:00",
            main = ForecastMain(
                feelLike = 18.0,
                grnd_level = 1005,
                humidity = 80,
                pressure = 1009,
                sea_level = 1010,
                temp = 19.5,
                temp_kf = 0.0,
                temp_max = 20.0,
                temp_min = 18.0
            ),
            weather = listOf(
                ForecastWeather(
                    description = "scattered clouds",
                    icon = "03d",
                    id = 802,
                    main = "Clouds"
                )
            ),
            clouds = null,
            rain = null,
            wind = null
        ),
        ForecastDetail(
            dt = 1732665600,
            dt_txt = "2024-11-27 00:00:00",
            main = ForecastMain(
                feelLike = 18.0,
                grnd_level = 1005,
                humidity = 80,
                pressure = 1009,
                sea_level = 1010,
                temp = 19.5,
                temp_kf = 0.0,
                temp_max = 20.0,
                temp_min = 18.0
            ),
            weather = listOf(
                ForecastWeather(
                    description = "scattered clouds",
                    icon = "03d",
                    id = 802,
                    main = "Clouds"
                )
            ),
            clouds = null,
            rain = null,
            wind = null
        ),
        /*ForecastDetail(
            dt = 1732665600,
            dt_txt = "2024-11-28 00:00:00",
            main = ForecastMain(
                feelLike = 18.0,
                grnd_level = 1005,
                humidity = 80,
                pressure = 1009,
                sea_level = 1010,
                temp = 19.5,
                temp_kf = 0.0,
                temp_max = 20.0,
                temp_min = 18.0
            ),
            weather = listOf(
                ForecastWeather(
                    description = "scattered clouds",
                    icon = "03d",
                    id = 802,
                    main = "Clouds"
                )
            ),
            clouds = null,
            rain = null,
            wind = null
        ),
        ForecastDetail(
            dt = 1633219200,
            dt_txt = "2024-10-11 12:00:00",
            main = ForecastMain(
                feelLike = 18.0,
                grnd_level = 1005,
                humidity = 80,
                pressure = 1009,
                sea_level = 1010,
                temp = 19.5,
                temp_kf = 0.0,
                temp_max = 20.0,
                temp_min = 18.0
            ),
            weather = listOf(
                ForecastWeather(
                    description = "scattered clouds",
                    icon = "03d",
                    id = 802,
                    main = "Clouds"
                )
            ),
            clouds = null,
            rain = null,
            wind = null
        )*/
    )
}