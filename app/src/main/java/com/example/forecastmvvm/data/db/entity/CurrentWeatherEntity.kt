package com.example.forecastmvvm.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CURRENT_WEATHER_ENTITY")
data class CurrentWeatherEntity(
    @ColumnInfo(name = "temp_c")
    val tempC: Double,
    @ColumnInfo(name = "temp_f")
    val tempF: Double,
    @ColumnInfo(name = "is_day")
    val isDay: Int,
    @Embedded(prefix = "condition_")
    val condition: ConditionEntity,
    @ColumnInfo(name = "wind_mph")
    val windMph: Double,
    @ColumnInfo(name = "wind_kph")
    val windKph: Double,
    @ColumnInfo(name = "wind_dir")
    val windDir: String,
    @ColumnInfo(name = "precip_mm")
    val precipMm: Double,
    @ColumnInfo(name = "precip_in")
    val precipIn: Double,
    @ColumnInfo(name = "feelslike_c")
    val feelslikeC: Double,
    @ColumnInfo(name = "feelslike_f")
    val feelslikeF: Double,
    @ColumnInfo(name = "vis_km")
    val visKm: Double,
    @ColumnInfo(name = "vis_miles")
    val visMiles: Double
) {

}