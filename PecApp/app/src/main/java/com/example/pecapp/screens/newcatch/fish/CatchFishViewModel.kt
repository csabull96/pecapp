package com.example.pecapp.screens.newcatch.fish

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pecapp.database.converters.Converters.Companion.convertDoubleToString
import com.example.pecapp.database.converters.Converters.Companion.convertStringToDouble
import com.example.pecapp.database.data.Catch
import com.example.pecapp.database.data.CatchBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class CatchFishViewModel(application: Application) :
    AndroidViewModel(application) {

    private lateinit var lastCatch: Catch

    var picture = MutableLiveData<String>()
    var type = MutableLiveData<String>()
    var weight = MutableLiveData<String>()
    var length = MutableLiveData<String>()

    private val _navigateToCatchMethod = MutableLiveData<Catch>()
    val navigateToCatchMethod : LiveData<Catch>
        get() = _navigateToCatchMethod

    var imageSetByUser: Boolean = false;

    fun initialize(catch: Catch) {
        lastCatch = catch

        picture.postValue(lastCatch.picture)
        type.postValue(lastCatch.type)
        weight.postValue(convertDoubleToString(lastCatch.weight))
        length.postValue(convertDoubleToString(lastCatch.length))
    }

    fun nextButtonPressed(drawable: Drawable) {
        val context = getApplication<Application>().applicationContext
        var picturePath = ""

        if (imageSetByUser) {
            try {
                val directory: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val file = File.createTempFile("pecapp_", ".jpg", directory).apply {
                    picturePath = absolutePath
                }
                val stream: OutputStream = FileOutputStream(file)
                val bitmap = (drawable as BitmapDrawable).bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
            }
            catch (exception: Exception) {
                Toast.makeText(context, "ERROR: couldn't save picture", Toast.LENGTH_LONG).show()
                exception.printStackTrace()
            }
        }

        lastCatch = CatchBuilder(lastCatch)
            .setPicture(picturePath)
            .setType(type.value)
            .setWeight(convertStringToDouble(weight.value))
            .setLength(convertStringToDouble(length.value))
            .build()

        _navigateToCatchMethod.postValue(lastCatch)
    }

    fun navigationFinished() {
        _navigateToCatchMethod.postValue(null)
    }
}
