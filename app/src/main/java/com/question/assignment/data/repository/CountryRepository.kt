package com.question.assignment.data.repository

import com.question.assignment.ui.model.CountryFlag
import org.json.JSONArray

object CountryRepository {
    private val countries = mutableListOf<CountryFlag>()

    // Load countries.json and parse the data
    fun loadCountries(jsonString: String) {
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val flagImage = jsonObject.getInt("flagImage")
            val flagName = jsonObject.getString("flagName")
            countries.add(CountryFlag(flagImage, flagName))
        }
    }

    fun getRandomCountry(): CountryFlag {
        return countries.random()
    }

    fun getCountries(): List<CountryFlag> {
        return countries
    }
}