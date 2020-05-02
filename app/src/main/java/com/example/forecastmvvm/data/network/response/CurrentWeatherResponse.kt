package com.example.forecastmvvm.data.network.response

import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)