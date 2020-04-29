package com.example.pecapp.database.data

import java.util.*

private const val DEFAULT_DOUBLE_VALUE = 0.0
private const val DEFAULT_STRING_VALUE = ""
private const val DEFAULT_LONG_VALUE = 0L

class CatchBuilder(private val catch: Catch = Catch()) {

    fun setLocation(location: String?): CatchBuilder {
        catch.location = location ?: DEFAULT_STRING_VALUE
        return this
    }

    fun setTemperature(temperature: Double?): CatchBuilder {
        catch.temperature = temperature ?: DEFAULT_DOUBLE_VALUE
        return this
    }

    fun setPressure(pressure: Double?): CatchBuilder {
        catch.pressure = pressure ?: DEFAULT_DOUBLE_VALUE
        return this
    }

    fun setHumidity(humidity: Double?): CatchBuilder {
        catch.humidity = humidity ?: DEFAULT_DOUBLE_VALUE
        return this
    }

    fun setDate(date: Long?): CatchBuilder {
        catch.date = date ?: DEFAULT_LONG_VALUE
        return this
    }

    fun setPicture(absolutePath: String?): CatchBuilder {
        catch.picture = absolutePath ?: DEFAULT_STRING_VALUE
        return this
    }

    fun setType(type: String?): CatchBuilder {
        catch.type = type ?: DEFAULT_STRING_VALUE
        return this
    }

    fun setWeight(weight: Double?): CatchBuilder {
        catch.weight = weight ?: DEFAULT_DOUBLE_VALUE
        return this
    }

    fun setLength(length: Double?): CatchBuilder {
        catch.length = length ?: DEFAULT_DOUBLE_VALUE
        return this
    }

    fun setBait(bait: String?): CatchBuilder {
        catch.bait = bait ?: DEFAULT_STRING_VALUE
        return this
    }

    fun setGroundbait(groundbait: String?): CatchBuilder {
        catch.groundbait = groundbait ?: DEFAULT_STRING_VALUE
        return this
    }

    fun setMethod(method: String?): CatchBuilder {
        catch.method = method ?: DEFAULT_STRING_VALUE
        return this
    }

    fun setDuration(duration: Double?): CatchBuilder {
        catch.duration = duration ?: DEFAULT_DOUBLE_VALUE
        return this
    }

    fun build(): Catch {
        return catch
    }
}