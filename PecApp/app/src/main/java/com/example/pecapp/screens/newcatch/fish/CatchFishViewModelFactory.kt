package com.example.pecapp.screens.newcatch.fish

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CatchFishViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatchFishViewModel::class.java)) {
            return CatchFishViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown VW class")
    }
}