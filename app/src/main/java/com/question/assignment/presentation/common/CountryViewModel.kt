package com.question.assignment.presentation.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.question.assignment.ui.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStreamReader

class CountryViewModel : ViewModel() {
    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> get() = _countries

    fun loadCountries(context: Context) {
        viewModelScope.launch {
            _countries.value = loadCountriesFromJson(context)
        }
    }

    private suspend fun loadCountriesFromJson(context: Context): List<Country> {
        return withContext(Dispatchers.IO) {
            val inputStream = context.assets.open("countries.json")
            val reader = InputStreamReader(inputStream)
            val jsonString = reader.readText()
            reader.close()

            val countryList = mutableListOf<Country>()
            val jsonObject = JSONObject(jsonString)
            val keys = jsonObject.keys()

            while (keys.hasNext()) {
                val key = keys.next()
                val name = jsonObject.getString(key)
                countryList.add(Country(key, name))
            }

            countryList
        }
    }
/*    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> get() = _countries

    fun loadCountries(context: Context) {
        viewModelScope.launch {
            _countries.value = loadCountriesFromJson(context)
        }
    }

    private suspend fun loadCountriesFromJson(context: Context): List<Country> {
        return withContext(Dispatchers.IO) {
            val inputStream = context.assets.open("flags.json")
            val reader = InputStreamReader(inputStream)
            val jsonString = reader.readText()
            reader.close()

            val countryList = mutableListOf<Country>()
            val jsonObject = JSONObject(jsonString)
            val keys = jsonObject.keys()

            while (keys.hasNext()) {
                val key = keys.next()
                val countryJson = jsonObject.getJSONObject(key)
                val wikiSource = countryJson.getString("wiki-source")
                val tsUsing = countryJson.getString("ts-using")
                val sources = countryJson.optJSONArray("sources")?.let { jsonArray ->
                    List(jsonArray.length()) { jsonArray.getString(it) }
                }

                countryList.add(Country(key, wikiSource, sources, tsUsing))
            }

            countryList
        }
    }*/
}
