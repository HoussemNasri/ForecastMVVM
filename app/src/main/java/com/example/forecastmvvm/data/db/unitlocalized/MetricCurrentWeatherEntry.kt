package com.example.forecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry(
    @ColumnInfo(name = "temp_c")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "wind_kph")
    override val windSpeed: Double,
    @ColumnInfo(name = "wind_dir")
    override val windDirection: String,
    @ColumnInfo(name = "precip_mm")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslike_c")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "vis_km")
    override val visibilityDistance: Double
) : UnitSpecificCurrentWeatherEntry