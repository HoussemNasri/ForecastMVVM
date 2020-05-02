package com.example.forecastmvvm.data.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.forecastmvvm.data.providers.UnitProvider
import com.example.forecastmvvm.internal.Constants
import com.example.forecastmvvm.internal.UnitSystem

class UnitProviderImpl(context: Context) : UnitProvider {
    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(Constants.PREFERENCE_UNIT_SYSTEM_KEY, UnitSystem.METRIC.name)

        return UnitSystem.valueOf(selectedName!!)
    }
}