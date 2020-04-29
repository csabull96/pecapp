package com.example.pecapp.screens.newcatch.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pecapp.database.converters.Converters.Companion.convertDoubleToString
import com.example.pecapp.database.converters.Converters.Companion.convertStringToDouble
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CatchBuilder
import com.example.pecapp.repository.PecAppRepository

class CatchWeatherViewModel(private val repository: PecAppRepository, application: Application) :
    AndroidViewModel(application) {

    private lateinit var lastCatch: Catch

    var temperature = MutableLiveData<String>()
    var pressure = MutableLiveData<String>()

    private val _navigateToCatchCertificate = MutableLiveData<Catch>()
    val navigateToCatchCertificate: LiveData<Catch>
        get() = _navigateToCatchCertificate

    fun initialize(catch: Catch) {
        lastCatch = catch
        temperature.postValue(convertDoubleToString(lastCatch.temperature))
        pressure.postValue(convertDoubleToString(lastCatch.pressure))

    }

    fun nextButtonPressed() {
        lastCatch = CatchBuilder(lastCatch)
            .setTemperature(convertStringToDouble(temperature.value))
            .setPressure(convertStringToDouble(pressure.value))
            .build()

        repository.insertCatch(lastCatch)

        _navigateToCatchCertificate.postValue(lastCatch)
    }

    fun navigationFinished() {
        _navigateToCatchCertificate.postValue(null)
    }
}