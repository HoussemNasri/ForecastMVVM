package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(
        isMetrix: Boolean
    ) : LiveData<out UnitSpecificCurrentWeatherEntry>
}