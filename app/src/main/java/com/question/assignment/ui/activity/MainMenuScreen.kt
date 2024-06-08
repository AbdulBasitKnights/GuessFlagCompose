package com.question.assignment.ui.activity

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.question.assignment.presentation.util.AppUtils.loadCountryFlags

@Composable
fun MainMenuScreen(context: Context, onButtonClick: (String) -> Unit) {
    var countryNames by remember { mutableStateOf(emptyList<String>()) }
    loadCountryFlags(context)

    LaunchedEffect(Unit) {
//            countryNames = loadCountriesFromJson(context)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onButtonClick("GuessTheCountry") }) {
            Text("Guess the Country")
        }
        Button(onClick = { onButtonClick("GuessHints") }) {
            Text("Guess-Hints")
        }
        Button(onClick = { onButtonClick("GuessTheFlag") }) {
            Text("Guess the Flag")
        }
        Button(onClick = { onButtonClick("AdvancedLevel") }) {
            Text("Advanced Level")
        }
    }
}
