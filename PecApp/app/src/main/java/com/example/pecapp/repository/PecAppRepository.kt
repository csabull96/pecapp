package com.example.pecapp.repository

import com.example.pecapp.database.PecAppDatabaseDao
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CurrentWeatherResponse
import com.example.pecapp.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PecAppRepository(
    private val pecAppDatabaseDao: PecAppDatabaseDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) {

    suspend fun getWeather(latitude: Double, longitude: Double): CurrentWeatherResponse? {
        return withContext(Dispatchers.IO) {
            weatherNetworkDataSource.fetchCurrentWeather(latitude, longitude)
        }
    }

    fun insertCatch(catch: Catch) {
        GlobalScope.launch(Dispatchers.IO) {
            pecAppDatabaseDao.insertCatch(catch)
        }
    }

    suspend fun getLastCatch(): Catch? {
        return withContext(Dispatchers.IO) {
            pecAppDatabaseDao.getLastCatch()
        }
    }

    fun updateCatch(catch: Catch) {
        GlobalScope.launch(Dispatchers.IO) {
            pecAppDatabaseDao.updateCatch(catch)
        }
    }

    suspend fun getAllCatches() = withContext(Dispatchers.IO) {
        pecAppDatabaseDao.getAllCatches()
    }

    fun clearAllCatches() = pecAppDatabaseDao.deleteAllCatches()

    private fun preserveFetchedCurrentWeather(fetchedCurrentWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            pecAppDatabaseDao.upsert(fetchedCurrentWeather)
        }
    }
}