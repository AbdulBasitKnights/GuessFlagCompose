package com.question.assignment.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
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
import java.util.Locale

class GuessFlagActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            GuessCountryScreen()
        }

    }
    @Composable
    fun GuessCountryScreen() {
        val viewModel: CountryViewModel = viewModel()
        val countries by viewModel.countries.collectAsState()
        var userInput by remember { mutableStateOf("") }
        var resultMessage by remember { mutableStateOf("") }
        val randomFlags by remember { mutableStateOf(ArrayList<String>(listOf("AF","PK","UK","US","SA","IN","GB","BN","AX","BB"))) }
        var currentCountryCode by remember { mutableStateOf(randomFlags.random()) } // Default code
        var selectedCountryIndex by remember { mutableStateOf(-1) } // No selection initially
        if (countries.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(
                        getDrawableResId(
                            LocalContext.current,
                            currentCountryCode
                        )
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    label = { Text("Search country") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Country list with search
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                ) {
                    items(countries.filter {
                        it.name.contains(
                            userInput,
                            ignoreCase = true
                        )
                    }) { country ->
                        Button(
                            onClick = {
                                selectedCountryIndex = countries.indexOf(country)
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedCountryIndex == countries.indexOf(
                                        country
                                    )
                                ) Color.Gray else Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = country.name,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (selectedCountryIndex != -1) {
                            val countryName = countries[selectedCountryIndex].code
                            resultMessage = if (currentCountryCode.equals(countryName, ignoreCase = true)) {
                                "Correct!"
                            } else {
                                "Incorrect. Try again."
                            }
                        }
                    },
                    modifier = Modifier.padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (resultMessage == "Correct!") Color.Green else Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(resultMessage)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    userInput = ""
                    resultMessage = ""
                    currentCountryCode = countries.random().code.lowercase(Locale.ROOT)
                }) {
                    Text("Next Flag")
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