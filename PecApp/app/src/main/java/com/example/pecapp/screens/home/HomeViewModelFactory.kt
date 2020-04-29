package com.example.pecapp.screens.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pecapp.repository.PecAppRepository
import java.lang.IllegalArgumentException

class HomeViewModelFactory(
    private val repository: PecAppRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown VW class")
    }
}