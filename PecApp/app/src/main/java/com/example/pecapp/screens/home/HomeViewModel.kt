package com.example.pecapp.screens.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pecapp.database.converters.Converters.Companion.convertDoubleToString
import com.example.pecapp.database.converters.Converters.Companion.convertStringToDouble
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CatchBuilder
import com.example.pecapp.repository.PecAppRepository
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PecAppRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _location = MutableLiveData<String>()
    val location : LiveData<String>
        get() = _location

    private val _temperature = MutableLiveData<String>()
    val temperature : LiveData<String>
        get() = _temperature

    private val _pressure = MutableLiveData<String>()
    val pressure : LiveData<String>
        get() = _pressure

    private val _humidity = MutableLiveData<String>()
    val humidity : LiveData<String>
        get() = _humidity

    private val _navigateToCatchDate = MutableLiveData<Catch>()
    val navigateToCatchDate : LiveData<Catch>
        get() = _navigateToCatchDate

    private var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)

    fun addButtonPressedShort() {
        val catch = createNewCatch()
        _navigateToCatchDate.postValue(catch)
    }

    fun addButtonPressedLong() {
        val catch = createNewCatch()
        repository.insertCatch(catch)
    }

    private fun createNewCatch(): Catch {
        return CatchBuilder(Catch())
            .setLocation(_location.value)
            .setTemperature(convertStringToDouble(_temperature.value))
            .setPressure(convertStringToDouble(_pressure.value))
            .setHumidity(convertStringToDouble(_humidity.value))
            .setDate(System.currentTimeMillis())
            .build()
    }

    fun navigationFinished() {
        _navigateToCatchDate.postValue(null)
    }

    fun getWeatherData() {

        var lat = 40.7831
        var long = -73.9712

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            it?.apply {
                lat = latitude
                long = longitude
            }
        }

        fusedLocationProviderClient.lastLocation.addOnFailureListener {
            Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = repository.getWeather(lat, long)

            currentWeatherResponse?.apply {
                _location.postValue(location.name)
                _temperature.postValue(convertDoubleToString(current.temperature))
                _pressure.postValue(convertDoubleToString(current.pressure))
                _humidity.postValue(convertDoubleToString(current.humidity))
            }
        }
    }
}