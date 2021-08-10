package com.muhammed.weatherapp.ui.view.fragment

import com.muhammed.weatherapp.data.model.WeatherModel

data class HomeItemViewState(val weatherModel: WeatherModel) {

    fun getDescription() = weatherModel.weather?.description

    fun getName() = weatherModel.name

    fun getTemp() = weatherModel.main?.temp

    fun getMinTemp() = weatherModel.main?.temp_min

    fun getMaxTemp() = weatherModel.main?.temp_max
}
