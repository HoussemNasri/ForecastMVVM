package com.example.forecastmvvm.data.providers

import com.example.forecastmvvm.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem () : UnitSystem
}