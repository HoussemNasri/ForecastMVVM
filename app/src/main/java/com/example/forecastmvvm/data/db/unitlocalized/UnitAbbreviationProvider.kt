package com.example.forecastmvvm.data.db.unitlocalized

class UnitAbbreviationProvider {
    companion object {
        fun chooseAbbreviatioon(isMetric: Boolean, property: Property): String {
            return when (property) {
                Property.SPEED -> chooseUnitAbbreviation(isMetric, "kph", "mph")
                Property.VOLUME -> chooseUnitAbbreviation(isMetric, "mm", "in")
                Property.TEMPERATURE -> chooseUnitAbbreviation(isMetric, "°C", "°F")
                Property.DISTANCE -> chooseUnitAbbreviation(isMetric, "km", "ml")
            }
        }

        enum class Property() {
            SPEED, DISTANCE, VOLUME, TEMPERATURE
        }

        private fun chooseUnitAbbreviation(
            isMetric: Boolean,
            metricAbbreviation: String,
            imperialAbbreviation: String

        ): String {
            return if (isMetric) metricAbbreviation else imperialAbbreviation
        }

    }
}