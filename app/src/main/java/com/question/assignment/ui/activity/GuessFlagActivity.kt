package com.question.assignment.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.ui.theme.QuestionAssignmentTheme

class GuessFlagActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                GuessCountryScreen()
        }

    }
    @Composable
    fun GuessCountryScreen() {
        val viewModel: CountryViewModel = viewModel()
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
            Button(onClick = { }) {
                Text("Guess the New Country")
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

}