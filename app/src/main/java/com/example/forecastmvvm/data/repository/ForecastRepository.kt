package com.example.forecastmvvm.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(
        unitSystem: MeasuringUnitSystem
    ) : LiveData<out UnitSpecificCurrentWeatherEntry>
}