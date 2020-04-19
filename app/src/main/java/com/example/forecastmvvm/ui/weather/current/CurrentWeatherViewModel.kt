package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.MeasuringUnitSystem
import com.example.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private fun getUnitSystem(): MeasuringUnitSystem {
        return MeasuringUnitSystem.METRIC
    }

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(getUnitSystem())

    }
}