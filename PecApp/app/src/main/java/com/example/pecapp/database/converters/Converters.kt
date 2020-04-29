package com.example.pecapp.database.converters

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.room.TypeConverter
import java.text.DecimalFormat
import java.util.Date

class Converters {

    @TypeConverter
    fun longToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time?.toLong()
    }



    companion object {
        fun convertStringToDouble(text: String?): Double {
            return text?.toDoubleOrNull() ?: 0.0
        }

        fun convertDoubleToString(double: Double?): String {
            return if (double != null && double.compareTo(0) == 0) "" else double!!.print(1)
        }

        private fun Double.print(decPlaces: Int): String {
            val decFormat = DecimalFormat("0.#")
            return decFormat.format(this)
        }

        @BindingAdapter("bindDoubleToText")
        @JvmStatic
        fun EditText.setTextFromDouble(double: Double) {
            if (double.compareTo(0) == 0) {
                setText("")
            }
            else {
                setText(double.toString())
            }
        }

        @InverseBindingAdapter(attribute = "bindDoubleToText", event = "android:textAttrChanged")
        @JvmStatic
        fun EditText.getDoubleFromText(): Double {
            return if (text.isEmpty()) 0.0 else text.toString().toDouble()
        }
    }
}