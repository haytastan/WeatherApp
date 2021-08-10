package com.muhammed.weatherapp.data.model

data class WeatherModel(
    val weather: Weather?,
    val name: String? = "",
    val main: Main?
)

data class Weather(
    val description: String
)
data class Main(
    val temp: Int,
    val temp_min: Int,
    val temp_max: Int
)
