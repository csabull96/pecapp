package com.example.pecapp.screens.catchlog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.pecapp.database.data.Catch
import com.example.pecapp.repository.PecAppRepository
import kotlinx.coroutines.*
import java.util.*

class CatchLogViewModel(private val repository: PecAppRepository, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val iOScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    val catches: LiveData<List<Catch>> = liveData(Dispatchers.IO) {
        val allCatches = repository.getAllCatches()
        emitSource(allCatches)
    }

    fun onClearDatabase() = iOScope.launch {
        repository.clearAllCatches()
    }

    private val _navigateToCatchCertificate = MutableLiveData<Long>()
    val navigateToCatchCertificate : LiveData<Long>
        get() = _navigateToCatchCertificate

    fun onCatchItemClicked(date: Long) {
        _navigateToCatchCertificate.value = date
    }

    fun onNavigatedToCatchDetails() {
        _navigateToCatchCertificate.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}