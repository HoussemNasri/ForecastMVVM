package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.forecastmvvm.data.network.response.WeatherLocation

interface ForecastRepository {

    suspend fun getCurrentWeather(
        isMetrix: Boolean
    ) : LiveData<out UnitSpecificCurrentWeatherEntry>

    suspend fun getWeatherLocation () : LiveData<WeatherLocation>
}