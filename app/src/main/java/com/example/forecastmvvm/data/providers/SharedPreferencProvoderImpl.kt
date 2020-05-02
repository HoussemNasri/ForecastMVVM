package com.example.forecastmvvm.data.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferencProvoderImpl(
    context: Context
) : SharedPreferencProvoder {
    private val appContext = context.applicationContext

    override fun getDefaultSharedPreference(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }
}