package com.example.forecastmvvm.ui.weather.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.db.unitlocalized.UnitAbbreviationProvider
import com.example.forecastmvvm.internal.glide.GlideApp
import com.example.forecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import kotlin.properties.Delegates


@Suppress("UNUSED_VARIABLE")
class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    private val TAG = "CurrentWeatherFragment"
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance<CurrentWeatherViewModelFactory>()
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var actionBar: ActionBar
    private var observed by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionBar = (activity as? AppCompatActivity)?.supportActionBar!!
        actionBar.title = ""
        actionBar.subtitle = ""
        Log.d(TAG, "Lifecycle : OnCreateView () ")
        observed = false
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun bindUI() = launch {
        onStartLoading()
        val currentWeather = viewModel.weather.await()

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null || observed)
                return@Observer

            updateDateToToday()
            //TODO remove the harcoded location
            updateLocation("Tunisia")
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText, it.conditionIconUrl)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)
            onStopLoading()
            Log.d(TAG, "Current Weather View Model Changed !!")
            observed = true
        })

    }

    private fun updateLocation(location: String) {
        actionBar.title = location
    }

    private fun updateDateToToday() {
        actionBar.subtitle = "Today"
    }

    @SuppressLint("SetTextI18n")
    private fun updateTemperature(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = UnitAbbreviationProvider.chooseAbbreviatioon(
            viewModel.isMetric(),
            UnitAbbreviationProvider.Companion.Property.TEMPERATURE
        )

        temperatureTextView.text = "$temperature$unitAbbreviation"
        feelsLikeTextView.text = "Feels Like : $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(
        conditionText: String,
        conditionUrlIcon: String = "",
        conditionCode: Int = -1
    ) {
        conditionTextView.text = conditionText
        GlideApp.with(this)
            .load(formatConditonUrl(conditionUrlIcon))
            .into(conditionIcon)
    }

    // Convert the icon resolution from 64x64 To 128x128
    fun formatConditonUrl(conditionUrl: String): String {
        val split = conditionUrl.split("64x64")
        return "https:${split[0]}128x128${split[1]}"
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrecipitation(pVolume: Double) {
        val unitAbbreviation = UnitAbbreviationProvider.chooseAbbreviatioon(
            viewModel.isMetric(),
            UnitAbbreviationProvider.Companion.Property.VOLUME
        )
        precipitationTextView.text = "Precipitation : $pVolume$unitAbbreviation"
    }


    @SuppressLint("SetTextI18n")
    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = UnitAbbreviationProvider.chooseAbbreviatioon(
            viewModel.isMetric(),
            UnitAbbreviationProvider.Companion.Property.SPEED
        )
        windTextView.text = "Wind : $windDirection, $windSpeed $unitAbbreviation"
    }

    @SuppressLint("SetTextI18n")
    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = UnitAbbreviationProvider.chooseAbbreviatioon(
            viewModel.isMetric(),
            UnitAbbreviationProvider.Companion.Property.DISTANCE
        )
        visibilityTextView.text = "Visibility : $visibilityDistance$unitAbbreviation"
    }

    fun onStartLoading() {
        loadingGroup.visibility = View.VISIBLE
    }

    fun onStopLoading() {
        loadingGroup.visibility = View.INVISIBLE
    }
}
