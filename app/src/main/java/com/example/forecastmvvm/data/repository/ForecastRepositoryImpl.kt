package com.example.forecastmvvm.data.repository

import android.util.Log
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

private const val TAG = "ForecastRepositoryImpl"

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource

) : ForecastRepository {
    init {
        weatherNetworkDataSource
            .downloadedCurrentWeather.observeForever {
                Log.d(TAG, "downloadedCurrentWeather")
                persistFetchedCurrentWeather(it)
            }
    }

    override suspend fun getCurrentWeather(isMetric : Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        Log.d(TAG, "getCurrentWeather")
        refreshWeatherData()
        return withContext(Dispatchers.IO) {
            return@withContext if (isMetric)
                currentWeatherDao.getWeatherMetric()
            else
                currentWeatherDao.getWeatherImperial()
        }

    }

    private suspend fun refreshWeatherData() {
        if (isRefreshCurrentNeeded(ZonedDateTime.now().minusMinutes(45))) {
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