package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.forecastmvvm.internal.MeasuringUnitSystem

interface ForecastRepository {

    suspend fun getCurrentWeather(
        unitSystem: MeasuringUnitSystem
    ) : LiveData<out UnitSpecificCurrentWeatherEntry>
}