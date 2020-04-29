package com.example.pecapp.screens.newcatch.location

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CatchLocationViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatchLocationViewModel::class.java)) {
            return CatchLocationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown VW class")
    }
}