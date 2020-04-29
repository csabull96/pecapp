package com.example.pecapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CURRENT_WEATHER_ID
import com.example.pecapp.database.data.CurrentWeatherResponse

@Dao
interface PecAppDatabaseDao {

    // catch related methods
    @Insert
    fun insertCatch(catch: Catch)

    @Update
    fun updateCatch(catch: Catch)

    @Query("SELECT * FROM CATCH_LOG_TABLE ORDER BY id DESC LIMIT 1")
    fun getLastCatch() : Catch?

    @Query("SELECT * FROM catch_log_table ORDER BY id DESC")
    fun getAllCatches(): LiveData<List<Catch>>

    @Query("DELETE FROM catch_log_table")
    fun deleteAllCatches()

//
//    @Delete
//    fun deleteCatch(catch: Catch)
//
//    @Query("SELECT * FROM catch_log_table WHERE id = :key")
//    fun get(key: Long): Catch?






    // weather related methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weather: CurrentWeatherResponse)



    @Query("SELECT * FROM weather_table WHERE id = $CURRENT_WEATHER_ID")
    fun getCurrentWeather(): CurrentWeatherResponse?


}