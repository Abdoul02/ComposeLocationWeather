package com.example.composelocationweather.feature_weather.domain.model.forecast

data class ForecastModel(
    //val city: City,  //No use at the moment
    val cnt: Int,
    val cod: String,
    val list: List<ForecastDetail>,
    val message: Int
) {
    constructor() : this(
        cnt = 1,
        cod = "",
        list = emptyList(),
        message = 1
    )
}