package com.example.forecastmvvm.ui.weather.current

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.db.mapper.CurrentWeatherMapper
import com.example.forecastmvvm.data.network.WeatherApiService
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
        val apiService = WeatherApiService()

        GlobalScope.launch (Dispatchers.Main){
            Log.d(TAG, "onActivityCreated = ")
            val currentWeatherResponse = apiService.getCurrentWeather("London").await()
            val currentWeatherEntity = CurrentWeatherMapper().mapToEntity(currentWeatherResponse.currentWeatherEntry)
            Log.d(TAG, "Entity : $currentWeatherEntity")
        }
    }

}
