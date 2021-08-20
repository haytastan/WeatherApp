package com.muhammed.weatherapp.ui.view.fragment

import com.muhammed.weatherapp.data.model.WeatherModel

data class HomeItemViewState(val weatherModel: WeatherModel) {

    fun getName() = weatherModel.name

    fun getHeat() = weatherModel.main.temp.toString()

    fun getHumidity() = weatherModel.main.humidity.toString()

    fun getWind() = weatherModel.wind.speed.toString()

    fun getVisibility() = weatherModel.visibility.toString() + " KM"

    fun getFeelsLike() = weatherModel.main.feels_like.toString()
}
