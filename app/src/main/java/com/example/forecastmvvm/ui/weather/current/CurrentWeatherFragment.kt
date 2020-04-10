package com.example.forecastmvvm.ui.weather.current

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.db.mapper.CurrentWeatherMapper
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.WeatherApiService
import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "CurrentWeatherFragment"

class CurrentWeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = WeatherApiService(ConnectivityInterceptorImpl(this.requireContext()))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)

        // observe network response
        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated = ")
            val currentWeatherEntity = CurrentWeatherMapper().mapToEntity(it.currentWeatherEntry)
            Log.d(TAG, "Entity : $currentWeatherEntity")
        })

        // send a network request
        GlobalScope.launch(Dispatchers.Main) {
            weatherNetworkDataSource.fetchCurrentWeather("London")
        }
    }

}
