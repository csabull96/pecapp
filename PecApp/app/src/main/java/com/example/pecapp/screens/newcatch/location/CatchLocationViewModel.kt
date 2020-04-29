package com.example.pecapp.screens.newcatch.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CatchBuilder

class CatchLocationViewModel(application: Application) :
    AndroidViewModel(application) {

    private lateinit var lastCatch: Catch

    var location = MutableLiveData<String>()

    private val _navigateToCatchWeather = MutableLiveData<Catch>()
    val navigateToCatchWeather: LiveData<Catch>
        get() = _navigateToCatchWeather

    fun initialize(catch: Catch) {
        lastCatch = catch
        location.value = lastCatch.location
    }

    fun nextButtonPressed() {
        lastCatch = CatchBuilder(lastCatch)
            .setLocation(location.value)
            .build()

        _navigateToCatchWeather.postValue(lastCatch)
    }

    fun navigationFinished() {
        _navigateToCatchWeather.postValue(null)
    }
}