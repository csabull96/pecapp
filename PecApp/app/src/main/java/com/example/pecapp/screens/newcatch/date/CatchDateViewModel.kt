package com.example.pecapp.screens.newcatch.date

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CatchBuilder

class CatchDateViewModel(application: Application) :
    AndroidViewModel(application) {

    private lateinit var lastCatch: Catch

    var lastCatchDate = MutableLiveData<Long>()

    private val _navigateToCatchFish = MutableLiveData<Catch>()
    val navigateToCatchFish : LiveData<Catch>
        get() = _navigateToCatchFish

    fun initialize(catch: Catch) {
        lastCatch = catch
        lastCatchDate.postValue(lastCatch.date)
    }

    fun nextButtonPressed() {
        lastCatch = CatchBuilder(lastCatch)
            .setDate(lastCatchDate.value)
            .build()

        _navigateToCatchFish.postValue(lastCatch)
    }

    fun navigationToCatchFishDone() {
        _navigateToCatchFish.postValue(null)
    }
}