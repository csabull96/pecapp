package com.example.pecapp.screens.catchlog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pecapp.repository.PecAppRepository

class CatchLogViewModelFactory(
    private val repository: PecAppRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatchLogViewModel::class.java)) {
            return CatchLogViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}