package com.muhammed.weatherapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.muhammed.weatherapp.data.repository.WeatherRepository
import com.muhammed.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    var lat = ""
    var lon = ""


    fun getWeather() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        Log.d(TAG, "homeViewModelLoading: ${lat}, ${lon}")

        try {
            emit(Resource.success(weatherRepository.getWeather(lat, lon)))
            Log.d(TAG, "getWeather25: $lat, $lon")
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message.toString()))
        }

    }
}