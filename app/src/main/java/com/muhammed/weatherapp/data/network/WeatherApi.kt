package com.muhammed.weatherapp.data.network

import com.muhammed.weatherapp.data.model.WeatherModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = "dff1fa39a54239e0b165641f3c814496",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "tr"
    ): Single<WeatherModel>

}