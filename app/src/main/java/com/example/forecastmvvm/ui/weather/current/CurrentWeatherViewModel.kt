package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.MeasuringUnitSystem
import com.example.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    fun isMetric(): Boolean {
        return true
    }

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric())
    }
}