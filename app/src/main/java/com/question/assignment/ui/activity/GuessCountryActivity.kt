package com.question.assignment.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.ui.model.Country
import java.util.Locale

class GuessCountryActivity : ComponentActivity() {
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
        val countries by viewModel.countries.collectAsState()
        var currentFlags by remember { mutableStateOf(emptyList<Country>()) }
        var correctCountry by remember { mutableStateOf<Country?>(null) }
        var resultMessage by remember { mutableStateOf("") }
        var resultColor by remember { mutableStateOf(Color.Transparent) }

        // Function to set new random flags and correct country
        fun setNewRound() {
            val randomCountries = countries.shuffled().take(3)
            currentFlags = randomCountries
            correctCountry = randomCountries.random()
            resultMessage = ""
            resultColor = Color.Transparent
        }

        // Initialize flags on first load
        LaunchedEffect(countries) {
            if (countries.isNotEmpty() && currentFlags.isEmpty()) {
                setNewRound()
            }
        }

        if (countries.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                correctCountry?.let {
                    Text(text = "Select the flag for: ${it.name}", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    currentFlags.forEach { country ->
                        Image(
                            painter = painterResource(
                                id = getDrawableResId(
                                    LocalContext.current,
                                    country.code
                                )
                            ),
                            contentDescription = country.name,
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    if (country == correctCountry) {
                                        resultMessage = "CORRECT!"
                                        resultColor = Color.Green
                                    } else {
                                        resultMessage = "WRONG!"
                                        resultColor = Color.Red
                                    }
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = resultMessage, color = resultColor, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { setNewRound() }) {
                    Text("Next")
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Loading...")
            }
        }
    }

    @Composable
    fun getDrawableResId(context: Context, countryCode: String): Int {
        return context.resources.getIdentifier(
            countryCode.lowercase(Locale.ROOT),
            "drawable",
            context.packageName
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewGuessCountryScreen() {
        GuessCountryScreen()
    }
}