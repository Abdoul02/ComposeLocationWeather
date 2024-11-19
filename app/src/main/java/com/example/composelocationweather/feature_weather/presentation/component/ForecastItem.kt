package com.example.composelocationweather.feature_weather.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelocationweather.R
import com.example.composelocationweather.feature_weather.domain.model.enums.WeatherTypes
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastClouds
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastDetail
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastMain
import com.example.composelocationweather.feature_weather.domain.model.forecast.ForecastWeather
import com.example.composelocationweather.util.DateUtils


@Composable
fun ForecastItem(
    modifier: Modifier = Modifier,
    forecastDetail: ForecastDetail,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = DateUtils.getDayOfWeekFromDate(forecastDetail.dt_txt) ?: forecastDetail.dt_txt,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Icon(
            painter = painterResource(id = getDrawable(forecastDetail.weather[0].main)),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(25.dp)
        )
        Text(
            text = stringResource(
                R.string.current_temp,
                forecastDetail.main.temp.toInt().toString(),
                "\u00B0"
            ),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

private fun getDrawable(temperatureDetail: String): Int {
    return when (temperatureDetail) {
        WeatherTypes.CLEAR.types -> {
            R.drawable.clear
        }

        WeatherTypes.CLOUD.types -> {
            R.drawable.ic_cloudy
        }

        WeatherTypes.RAIN.types -> {
            R.drawable.rain
        }

        else -> R.drawable.partlysunny
    }
}

@Preview
@Composable
fun ForecastItemPreview() {
    val forecastDetail = ForecastDetail(
        clouds = ForecastClouds(1),
        dt = 1731985200,
        dt_txt = "2024-11-19 03:00:00",
        main = ForecastMain(
            feelLike = 14.31,
            grnd_level = 994,
            humidity = 86,
            pressure = 1005,
            sea_level = 1005,
            temp = 14.55
        ),
        rain = null,
        sys = null,
        weather = listOf(
            ForecastWeather(
                id = 803,
                description = "broken clouds",
                main = "clouds",
                icon = "04n"
            )
        ),
        wind = null
    )
    ForecastItem(forecastDetail = forecastDetail)
}

