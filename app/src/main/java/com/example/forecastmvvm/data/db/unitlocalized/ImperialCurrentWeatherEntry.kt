package com.example.forecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

data class ImperialCurrentWeatherEntry(
    @ColumnInfo(name = "temp_f")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "wind_mph")
    override val windSpeed: Double,
    @ColumnInfo(name = "wind_dir")
    override val windDirection: String,
    @ColumnInfo(name = "precip_in")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslike_f")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "vis_miles")
    override val visibilityDistance: Double
) : UnitSpecificCurrentWeatherEntry