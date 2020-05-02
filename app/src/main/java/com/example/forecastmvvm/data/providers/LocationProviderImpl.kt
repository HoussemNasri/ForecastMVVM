package com.example.forecastmvvm.data.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.forecastmvvm.data.network.response.WeatherLocation

class LocationProviderImpl(
    context: Context
) : LocationProvider {

    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocdationString(): String {
        return "Los Angeles"
    }

}