package com.question.assignment.ui.activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.question.assignment.presentation.common.CountryViewModel

@Composable
fun MainMenuScreen() {
    val context = LocalContext.current
    val viewModel: CountryViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.loadCountries(context)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            context.startActivity(Intent(context,GuessCountryActivity::class.java)) }) {
            Text("Guess the Country")
        }
        Button(onClick = {context.startActivity(Intent(context,GuessHintsActivity::class.java)) }) {
            Text("Guess-Hints")
        }
        Button(onClick = {context.startActivity(Intent(context,GuessFlagActivity::class.java)) }) {
            Text("Guess the Flag")
        }
        Button(onClick = { context.startActivity(Intent(context,AdvancedLevelActivity::class.java))  }) {
            Text("Advanced Level")
        }

    }
}

