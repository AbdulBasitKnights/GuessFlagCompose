package com.question.assignment.presentation.common

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.question.assignment.presentation.util.countriesData
import com.question.assignment.ui.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStreamReader

class CountryViewModel(application: Application) : AndroidViewModel(application) {
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
            countriesData.value=countryList
            countryList
        }
    }
}
