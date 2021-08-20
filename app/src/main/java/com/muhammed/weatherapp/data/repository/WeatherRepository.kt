package com.muhammed.weatherapp.data.repository

import com.muhammed.weatherapp.data.model.WeatherModel
import com.muhammed.weatherapp.data.network.WeatherApi
import com.muhammed.weatherapp.util.Resource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

     fun getWeather(lat: Double, lon: Double): Single<WeatherModel> {
          return weatherApi.getWeather(lat,lon)
     }
}