package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.providers.UnitProvider
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.example.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModel() {
    fun isMetric(): Boolean {
        return unitProvider.getUnitSystem() == UnitSystem.METRIC
    }

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric())
    }
}