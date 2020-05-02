package com.example.forecastmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.CurrentWeatherDao
import com.example.forecastmvvm.data.db.WeatherLocationDao
import com.example.forecastmvvm.data.db.mapper.CurrentWeatherMapper
import com.example.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.data.network.response.WeatherLocation
import com.example.forecastmvvm.data.providers.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

private const val TAG = "ForecastRepositoryImpl"

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider

) : ForecastRepository {
    init {
        weatherNetworkDataSource
            .downloadedCurrentWeather.observeForever {
                Log.d(TAG, "downloadedCurrentWeather")
                persistFetchedCurrentWeather(it)
            }
    }

    override suspend fun getCurrentWeather(isMetric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        Log.d(TAG, "getCurrentWeather")
        return withContext(Dispatchers.IO) {
            refreshWeatherData()
            return@withContext if (isMetric)
                currentWeatherDao.getWeatherMetric()
            else
                currentWeatherDao.getWeatherImperial()
        }

    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            refreshWeatherData()
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private suspend fun refreshWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)
            || isRefreshCurrentNeeded(lastWeatherLocation.zonedDateTime)
        ) {
            fetchCurrentWeather()
        }


    }


    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(CurrentWeatherMapper().mapToEntity(fetchedWeather.currentWeatherEntry))
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocdationString(),
            Locale.getDefault().language
        )
    }

    private fun isRefreshCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val _30MinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(_30MinutesAgo)
    }


}