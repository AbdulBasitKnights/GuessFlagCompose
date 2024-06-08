package com.question.assignment.ui.activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainMenuScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("GuessCountryScreen") }) {
            Text("Guess the Country")
        }
        Button(onClick = { navController.navigate("GuessHintScreen") }) {
            Text("Guess-Hints")
        }
        Button(onClick = { navController.navigate("GuessTheFlagScreen") }) {
            Text("Guess the Flag")
        }
        Button(onClick = { navController.navigate("AdvancedLevelScreen") }) {
            Text("Advanced Level")
        }

    }
}

