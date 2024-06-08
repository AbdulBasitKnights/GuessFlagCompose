package com.question.assignment.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.question.assignment.ui.model.CountryFlag

@Composable
fun AdvancedLevelScreen(countries: List<CountryFlag>, onResult: (Boolean, List<CountryFlag>) -> Unit) {
    val randomCountries = countries.shuffled().take(3)
    var guesses by remember { mutableStateOf(Array(3) { "" }) }
    var attemptsLeft by remember { mutableIntStateOf(3) }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var submitLabel by remember { mutableStateOf("Submit") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        randomCountries.forEachIndexed { index, country ->

            Image(
                painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/hjnilsson/country-flags/master/png1000px/${country.flagName.lowercase()}.png"),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            OutlinedTextField(
                value = guesses[index],
                onValueChange = { guesses[index] = it },
                label = { Text("Country Name") },
                enabled = resultMessage == null || country.flagName.equals(guesses[index], ignoreCase = true)
            )
        }

        if (resultMessage == null) {
            Button(onClick = {
                attemptsLeft--
                val correctCount = randomCountries.zip(guesses) { country, guess ->
                    country.flagName.equals(guess, ignoreCase = true)
                }.count { it }
                if (correctCount == 3) {
                    resultMessage = "CORRECT!"
                } else if (attemptsLeft == 0) {
                    resultMessage = "WRONG!"
                }
                submitLabel = "Next"
            }) {
                Text(submitLabel)
            }
        } else {
            Text(
                text = resultMessage!!,
                color = if (resultMessage == "CORRECT!") Color.Green else Color.Red,
                fontSize = 24.sp
            )
            randomCountries.forEachIndexed { index, country ->
                Text(
                    text = "Country ${index + 1}: ${country.flagName}",
                    color = if (resultMessage == "CORRECT!" || country.flagName.equals(guesses[index], ignoreCase = true)) Color.Green else Color.Blue,
                    fontSize = 20.sp
                )
            }
            Button(onClick = { onResult(resultMessage == "CORRECT!", randomCountries) }) {
                Text("Next")
            }
        }
    }
}