package com.example.forecastmvvm.data.db.mapper

import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntity
import com.example.forecastmvvm.data.network.response.CurrentWeatherEntry

class CurrentWeatherMapper : Mapper<CurrentWeatherEntity, CurrentWeatherEntry> {
    override fun mapFromEntity(type: CurrentWeatherEntity): CurrentWeatherEntry {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(type: CurrentWeatherEntry): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            type.tempC, type.tempF, type.isDay, ConditionMapper().mapToEntity(type.condition),
            type.windMph, type.windKph, type.windDir, type.precipMm, type.precipIn,
            type.feelslikeC, type.feelslikeF, type.visKm, type.visMiles
        )
    }
}