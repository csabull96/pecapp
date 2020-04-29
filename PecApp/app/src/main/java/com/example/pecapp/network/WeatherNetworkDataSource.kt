package com.example.pecapp.network

import com.example.pecapp.database.data.CurrentWeatherResponse
import java.lang.Exception

class WeatherNetworkDataSource(private val weatherApiService: WeatherApiService) {

    suspend fun fetchCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherResponse? {
        var weatherResponse: CurrentWeatherResponse? = null

        try {
            weatherResponse = weatherApiService
                .getCurrentWeather(location = "${latitude.toString()}, ${longitude.toString()}")
                .await();
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        return weatherResponse
    }
}