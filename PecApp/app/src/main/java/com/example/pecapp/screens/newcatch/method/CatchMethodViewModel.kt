package com.example.pecapp.screens.newcatch.method

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pecapp.database.converters.Converters.Companion.convertDoubleToString
import com.example.pecapp.database.converters.Converters.Companion.convertStringToDouble
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CatchBuilder

class CatchMethodViewModel(application: Application) :
    AndroidViewModel(application) {

    private lateinit var lastCatch: Catch

    var bait = MutableLiveData<String>()
    var groundbait = MutableLiveData<String>()
    var method = MutableLiveData<String>()
    var duration = MutableLiveData<String>()

    private val _navigateToCatchLocation = MutableLiveData<Catch>()
    val navigateToCatchLocation : LiveData<Catch>
        get() = _navigateToCatchLocation

    fun initialize(catch: Catch) {
        lastCatch = catch

        bait.value = lastCatch.bait
        groundbait.value = lastCatch.groundbait
        method.value = lastCatch.method
        duration.value = convertDoubleToString(lastCatch.duration)
    }

    fun navigationFinished() {
        _navigateToCatchLocation.postValue(null)
    }

    fun nextButtonPressed() {

        lastCatch = CatchBuilder(lastCatch)
            .setBait(bait.value)
            .setGroundbait(groundbait.value)
            .setMethod(method.value)
            .setDuration(convertStringToDouble(duration.value))
            .build()

        _navigateToCatchLocation.postValue(lastCatch)
    }
}