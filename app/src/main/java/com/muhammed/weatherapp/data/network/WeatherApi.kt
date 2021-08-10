package com.muhammed.weatherapp.data.network

import com.muhammed.weatherapp.data.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = "dff1fa39a54239e0b165641f3c814496",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "tr"
    ): WeatherModel

}