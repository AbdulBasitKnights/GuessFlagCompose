package com.question.assignment.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainMenuScreen(onButtonClick: (String) -> Unit) {
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
