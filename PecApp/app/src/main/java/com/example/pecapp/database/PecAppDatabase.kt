package com.example.pecapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CurrentWeatherResponse

@Database(entities = [Catch::class, CurrentWeatherResponse::class], version = 1, exportSchema = false)
abstract class PecAppDatabase : RoomDatabase() {

    abstract val pecAppDatabaseDao: PecAppDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: PecAppDatabase? = null

        fun getInstance(context: Context): PecAppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            PecAppDatabase::class.java,
                            "pec_app_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance;
            }
        }
    }
}