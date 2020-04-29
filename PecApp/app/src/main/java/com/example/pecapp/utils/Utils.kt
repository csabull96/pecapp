package com.example.pecapp.utils

import com.example.pecapp.database.data.Catch

class Utils {
    companion object {
        fun Catch?.isNull() = this == null
        fun Catch?.isNotNull() = this != null
    }
}