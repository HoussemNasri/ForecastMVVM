package com.example.forecastmvvm.data.providers

import android.content.SharedPreferences

interface SharedPreferencProvoder {
    fun getDefaultSharedPreference() : SharedPreferences
}