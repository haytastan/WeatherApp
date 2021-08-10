package com.muhammed.weatherapp.data.repository

import com.muhammed.weatherapp.data.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getWeather(lat: String, lon: String) = weatherApi.getWeather(lat, lon)
}