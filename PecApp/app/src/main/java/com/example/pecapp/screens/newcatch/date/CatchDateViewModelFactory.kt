package com.example.pecapp.screens.newcatch.date

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CatchDateViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatchDateViewModel::class.java)) {
            return CatchDateViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown VW class")
    }
}