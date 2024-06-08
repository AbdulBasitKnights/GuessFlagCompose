package com.question.assignment.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.question.assignment.ui.model.CountryFlag

@Composable
fun GuessTheCountryScreen(countries: List<CountryFlag>, onAnswerSubmit: (Boolean, CountryFlag) -> Unit) {
    var selectedCountry by remember { mutableStateOf<CountryFlag?>(null) }
    val randomCountry = remember { countries.random() }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberImagePainter("https://raw.githubusercontent.com/hjnilsson/country-flags/master/png1000px/${randomCountry.flagName.lowercase()}.png"),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        DropdownMenu(
            expanded = true,
            onDismissRequest = { /*TODO*/ },
        ) {
            countries.forEach { country ->
                DropdownMenuItem(onClick = { selectedCountry = country }) {
                    Text(country.flagName)
                }
            }
        }
        var submitLabel by remember { mutableStateOf("Submit") }
        Button(onClick = {
            selectedCountry?.let {
                val isCorrect = it == randomCountry
                onAnswerSubmit(isCorrect, randomCountry)
                submitLabel = "Next"
            }
        }) {
            Text(submitLabel)
        }
    }
}
