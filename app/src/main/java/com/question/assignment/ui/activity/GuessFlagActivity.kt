package com.question.assignment.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.presentation.util.countriesData
import java.io.InputStream
import java.util.Locale

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
        val countries by viewModel.countries.collectAsState()
        var currentCountryCode by remember { mutableStateOf("ad") }
        var userInput by remember { mutableStateOf("") }
        var resultMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (countries.isNotEmpty()) {
                val countryName = countries.find { it.code.equals(currentCountryCode, true) }?.name ?: ""

                Image(
                    painter = painterResource(getDrawableResId(LocalContext.current, currentCountryCode)),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    label = { Text("Enter country name") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    resultMessage = if (userInput.equals(countryName, ignoreCase = true)) {
                        "Correct!"
                    } else {
                        "Incorrect. Try again."
                    }
                }) {
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
            } else {
                Text("Loading...")
            }
        }
    }

    @DrawableRes
    fun getDrawableResId(context: Context, countryCode: String): Int {
        return context.resources.getIdentifier(countryCode.lowercase(Locale.ROOT), "drawable", context.packageName)
    }
    }