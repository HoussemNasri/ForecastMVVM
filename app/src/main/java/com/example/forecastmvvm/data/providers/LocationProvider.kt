package com.example.forecastmvvm.data.providers

import com.example.forecastmvvm.data.network.response.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocdationString(): String
}