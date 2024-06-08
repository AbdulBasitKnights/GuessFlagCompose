package com.question.assignment.presentation.util

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

object AppUtils {
//    suspend fun loadCountriesFromJson(context: Context): List<String> {
//        val inputStream = withContext(Dispatchers.IO) {
//            context.assets.open("countries.json")
//        }
//        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
//        val stringBuilder = StringBuilder()
//        bufferedReader.useLines { lines ->
//            lines.forEach {
//                stringBuilder.append(it)
//            }
//        }
//        val jsonObject = JSONObject(stringBuilder.toString())
//        val countryNames = mutableListOf<String>()
//        jsonObject.keys().forEach { key ->
//            countryNames.add(jsonObject.getString(key))
//        }
//        return countryNames
//    }

    // Function to load country flags URLs from drawable resources
    fun loadCountryFlags(context: Context): List<String> {
        val countryFlags = mutableListOf<String>()
        val drawableNames = listOf(
            "ad", "ae", "af", /* Add all country codes */
        )
        drawableNames.forEach { name ->
            val resourceId = context.resources.getIdentifier(name, "drawable", context.packageName)
            if (resourceId != 0) {
                countryFlags.add("drawable/$name")
            }
        }
        return countryFlags
    }
}