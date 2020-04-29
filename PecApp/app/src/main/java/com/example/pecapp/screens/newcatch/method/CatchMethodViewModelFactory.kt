package com.example.pecapp.screens.newcatch.method

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CatchMethodViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatchMethodViewModel::class.java)) {
            return CatchMethodViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown VW class")
    }
}