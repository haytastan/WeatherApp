package com.muhammed.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammed.weatherapp.data.model.WeatherModel
import com.muhammed.weatherapp.data.repository.WeatherRepository
import com.muhammed.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

     val  weather = MutableLiveData<Resource<WeatherModel>>()
    private val compositDisposable = CompositeDisposable()

    fun getWeather(lat: Double,lon: Double)  {

        weather.postValue(Resource.loading(null))
            compositDisposable.add(
                weatherRepository.getWeather(lat,lon)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( { getweather ->
                        weather.postValue(Resource.success(getweather))
                    }, {
                        weather.postValue(Resource.error("Bir problemimiz var",null))
                    })
            )
       }

    override fun onCleared() {
        super.onCleared()
        compositDisposable.dispose()
    }

}