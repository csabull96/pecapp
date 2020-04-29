package com.example.pecapp.screens.newcatch.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pecapp.repository.PecAppRepository
import java.lang.IllegalArgumentException

class CatchWeatherViewModelFactory(
    private val repository: PecAppRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatchWeatherViewModel::class.java)) {
            return CatchWeatherViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown VW class")
    }
}