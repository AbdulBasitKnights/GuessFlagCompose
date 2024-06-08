package com.question.assignment.ui.activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.ui.model.Country

@Composable
fun GuessCountryScreen(navController: NavController) {
    val viewModel: CountryViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadCountries(context)
    }
    val countries by viewModel.countries.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("GuessCountryScreen") }) {
            Text("Guess the New Country")
        }
        Button(onClick = { navController.navigate("GuessHintsScreen") }) {
            Text("Guess-Hints New")
        }
        Button(onClick = { navController.navigate("GuessTheFlagsScreen") }) {
            Text("Guess the Flag New")
        }
        Button(onClick = { navController.navigate("AdvancedLevelsScreen") }) {
            Text("Advanced Level")
        }

    }
    if (countries.isNotEmpty()) {
        var selectedCountry by remember { mutableStateOf("") }

        BasicTextField(
            value = selectedCountry,
            onValueChange = { selectedCountry = it }
        )

        Button(onClick = { /* Submit answer */ }) {
            Text("Submit")
        }
    }
}

