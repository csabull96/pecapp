package com.example.pecapp.network

import com.example.pecapp.database.data.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://api.weatherstack.com/"
private const val API_KEY = "2a6a77a5221bdb7fa94d127de4b3eb7e"

// http://api.weatherstack.com/current?access_key=2a6a77a5221bdb7fa94d127de4b3eb7e&query=Tardos

private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("access_key") key: String = API_KEY,
        @Query("query") location: String
    ): Deferred<CurrentWeatherResponse>
}

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

//    companion object {
//        operator fun invoke(): WeatherApiService {
//            val requestInterceptor = Interceptor { chain ->
//
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .addQueryParameter("access_key", API_KEY)
//                    .build()
//
//                val request = chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//
//                return@Interceptor chain.proceed(request)
//            }
//
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(requestInterceptor)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(WeatherApiService::class.java)
//        }
//    }
