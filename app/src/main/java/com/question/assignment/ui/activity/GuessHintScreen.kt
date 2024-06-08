package com.question.assignment.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun GuessHintsScreen(country: CountryFlag, onResult: (Boolean, CountryFlag) -> Unit) {
    val countryName = country.flagName.uppercase()
    var guessedCharacters by remember { mutableStateOf(CharArray(countryName.length) { '-' }) }
    var attemptsLeft by remember { mutableStateOf(3) }
    var currentInput by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var submitLabel by remember { mutableStateOf("Submit") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/hjnilsson/country-flags/master/png1000px/${country.flagName.lowercase()}.png"),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Text(text = guessedCharacters.concatToString(), fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        if (resultMessage == null) {
            OutlinedTextField(
                value = currentInput,
                onValueChange = { currentInput = it.uppercase().take(1) },
                label = { Text("Enter a character") },
                singleLine = true
            )
            Button(onClick = {
                val char = currentInput.firstOrNull() ?: return@Button
                currentInput = ""

                if (char in countryName) {
                    countryName.forEachIndexed { index, c ->
                        if (c == char) guessedCharacters[index] = c
                    }
                } else {
                    attemptsLeft--
                }

                if (guessedCharacters.concatToString() == countryName) {
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
            Text(text = "The correct country is: ${country.flagName}", color = Color.Blue, fontSize = 20.sp)
            Button(onClick = { onResult(resultMessage == "CORRECT!", country) }) {
                Text("Next")
            }
        }
    }
}
