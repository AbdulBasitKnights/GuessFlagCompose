package com.question.assignment.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.question.assignment.ui.model.CountryFlag

@Composable
fun ResultScreen(navController: NavController, isCorrect: Boolean, correctCountry: CountryFlag, onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isCorrect) "CORRECT!" else "WRONG!",
            color = if (isCorrect) Color.Green else Color.Red,
            fontSize = 24.sp
        )
        Text(
            text = "The correct country is: ${correctCountry.flagName}",
            color = Color.Blue,
            fontSize = 20.sp
        )
        Button(onClick = onNext) {
            Text("Next")
        }
    }
}
