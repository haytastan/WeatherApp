package com.muhammed.weatherapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.muhammed.weatherapp.data.repository.WeatherRepository
import com.muhammed.weatherapp.ui.view.fragment.HomeFragment
import com.muhammed.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    var latt = ""
    var lonn = ""


    fun getWeather() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        Log.d(TAG, "homeViewModelLoading: ${latt}, ${lonn}")


        try {
            emit(Resource.success(weatherRepository.getWeather(latt, lonn)))
            Log.d(TAG, "getWeather25: $latt, $lonn")

        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message.toString()))
        }

    }
}