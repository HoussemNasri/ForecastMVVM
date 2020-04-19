package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.CurrentWeatherDao
import com.example.forecastmvvm.data.db.mapper.CurrentWeatherMapper
import com.example.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.internal.MeasuringUnitSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource

) : ForecastRepository {
    init {
        weatherNetworkDataSource
            .downloadedCurrentWeather.observeForever {
                persistFetchedCurrentWeather(it)
            }
    }

    override suspend fun getCurrentWeather(unitSystem: MeasuringUnitSystem): LiveData<out UnitSpecificCurrentWeatherEntry> {
        refreshWeatherData()
        return withContext(Dispatchers.IO) {
            return@withContext if (unitSystem == MeasuringUnitSystem.IMPERIAL)
                currentWeatherDao.getWeatherImperial()
            else
                currentWeatherDao.getWeatherMetric()
        }

    }

    private suspend fun refreshWeatherData() {
        if (isRefreshCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchCurrentWeather()
        }
    }


    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(CurrentWeatherMapper().mapToEntity(fetchedWeather.currentWeatherEntry))
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            location = "Los Angelos",
            languageCode = "en"
        )
    }

    private fun isRefreshCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val _30MinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(_30MinutesAgo)
    }


}