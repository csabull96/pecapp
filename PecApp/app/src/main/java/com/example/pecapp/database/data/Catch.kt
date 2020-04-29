package com.example.pecapp.database.data

import android.os.Parcelable
import androidx.room.*
import com.example.pecapp.database.converters.Converters
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "catch_log_table")
@TypeConverters(Converters::class)
@Parcelize
data class Catch(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name="picture")
    var picture: String = "",

    @ColumnInfo(name = "type")
    var type: String = "",
    @ColumnInfo(name = "weight")
    var weight: Double = 0.0,
    @ColumnInfo(name = "length")
    var length: Double = 0.0,

    @ColumnInfo(name = "temperature")
    var temperature: Double = 0.0,
    @ColumnInfo(name = "atmospheric_pressure")
    var pressure: Double = 0.0,
    @ColumnInfo(name = "humidity")
    var humidity: Double = 0.0,

    @ColumnInfo(name = "bait")
    var bait: String = "",
    @ColumnInfo(name = "groundbait")
    var groundbait: String = "",
    @ColumnInfo(name = "method")
    var method: String = "",
    @ColumnInfo(name = "duration")
    var duration: Double = 0.0,

    @ColumnInfo(name = "date")
    var date: Long = 0L,
    @ColumnInfo(name = "location")
    var location: String = "",

    @ColumnInfo(name = "note")
    var note: String = ""
) : Parcelable