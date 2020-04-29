package com.example.pecapp.database.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0;

@Entity(tableName = "weather_table")
data class CurrentWeatherResponse(
    @Embedded(prefix = "request_")
    val request: Request,
    @Embedded(prefix = "location_")
    val location: Location,
    @Embedded(prefix = "weather_")
    val current: Weather
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}

data class Request(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)

data class Location(
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("utc_offset")
    val utcOffset: String
)

data class Weather(
    @SerializedName("observation_time")
    val observationTime: String,
    val temperature: Double,
    @SerializedName("weather_code")
    val weatherCode: Int,
    //@SerializedName("weather_icons")
    //val weatherIcons: List<String>,
    //@SerializedName("weather_descriptions")
    //val weatherDescriptions: List<String>,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_degree")
    val windDegree: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    val pressure: Double,
    val precip: Double,
    val humidity: Double,
    val cloudcover: Double,
    val feelslike: Double,
    @SerializedName("uv_index")
    val uvIndex: Double,
    val visibility: Double,
    @SerializedName("is_day")
    val isDay: String
)