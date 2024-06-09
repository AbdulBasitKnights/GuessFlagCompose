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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.presentation.util.countriesData
import com.question.assignment.ui.model.Country
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
        var userInput by remember { mutableStateOf("") }
        var resultMessage by remember { mutableStateOf("") }
        var currentCountryCode by remember { mutableStateOf("AD") } // Default code
        var selectedCountryIndex by remember { mutableStateOf(0) }
        var expanded by remember { mutableStateOf(false) }
        if(!countries.isNullOrEmpty()){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(getDrawableResId(LocalContext.current, currentCountryCode)),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dropdown for country selection
                Box {
                    Button(onClick = { expanded = true }) {
                        Text("Select Country")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        countries.forEachIndexed { index, country ->
                            DropdownMenuItem(onClick = {
                                selectedCountryIndex = index
                                expanded = false
                            }) {
                                Text(country.name)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = countries[selectedCountryIndex].name,
                    onValueChange = {userInput=it },
                    label = { Text("Enter country name") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val countryName = countries[selectedCountryIndex].name
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
            }
        }

    }

    @Composable
    fun getDrawableResId(context: Context, countryCode: String): Int {
        return context.resources.getIdentifier(countryCode.lowercase(Locale.ROOT), "drawable", context.packageName)
    }
}