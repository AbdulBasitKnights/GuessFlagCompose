package com.question.assignment.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.question.assignment.R
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.ui.model.Country
import java.util.Locale

class GuessHintsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            GuessHintsScreen()
        }

    }
    @Composable
    fun GuessHintsScreen() {
        val viewModel: CountryViewModel = viewModel()
        val countries by viewModel.countries.collectAsState(initial = emptyList())
        var currentCountry by remember { mutableStateOf<Country?>(null) }
        var displayText by remember { mutableStateOf("") }
        var userInput by remember { mutableStateOf(TextFieldValue("")) }
        var incorrectGuesses by remember { mutableStateOf(0) }
        var resultMessage by remember { mutableStateOf("") }
        var resultColor by remember { mutableStateOf(Color.Transparent) }
        var buttonText by remember { mutableStateOf("Submit") }

        // Function to initialize a new round
        fun setNewRound() {
            if (countries.isNotEmpty()) {
                currentCountry = countries.random()
                displayText = "_".repeat(currentCountry!!.name.length)
                userInput = TextFieldValue("")
                incorrectGuesses = 0
                resultMessage = ""
                resultColor = Color.Transparent
                buttonText = "Submit"
            }
        }

        // Initialize on first load
        LaunchedEffect(countries) {
            if (countries.isNotEmpty() && currentCountry == null) {
                setNewRound()
            }
        }

        if (countries.isNotEmpty()) {
            // Background drawable
            val backgroundDrawable: Painter = painterResource(id = R.drawable.appbg)

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Background Image
                Image(
                    painter = backgroundDrawable,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillHeight
                )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                currentCountry?.let { country ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(
                            id = getDrawableResId(
                                LocalContext.current,
                                country.code
                            )
                        ),
                        contentDescription = country.name,
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = displayText, fontSize = 24.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        label = { Text("Enter a character") },
                        maxLines = 1,
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (buttonText == "Submit") {
                                val char = userInput.text.firstOrNull()?.uppercaseChar()
                                if (char != null) {
                                    if (country.name.uppercase().contains(char)) {
                                        displayText = country.name.map { c ->
                                            if (c.uppercaseChar() == char) c else displayText[country.name.indexOf(
                                                c
                                            )]
                                        }.joinToString("")
                                        if (displayText.equals(country.name, ignoreCase = true)) {
                                            resultMessage = "CORRECT!"
                                            resultColor = Color.Green
                                            buttonText = "Next"
                                        }
                                    } else {
                                        incorrectGuesses++
                                        if (incorrectGuesses >= 3) {
                                            resultMessage =
                                                "WRONG! The correct name is ${country.name}"
                                            resultColor = Color.Red
                                            buttonText = "Next"
                                        }
                                    }
                                }
                            } else {
                                setNewRound()
                            }
                            userInput = TextFieldValue("")
                        },
                        modifier = Modifier.padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = resultColor.takeIf { it != Color.Transparent }
                                ?: Color.Gray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(buttonText)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = resultMessage, color = resultColor, fontSize = 18.sp)
                }
            }
        }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Loading...")
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun PreviewGuessCountryScreen() {
        GuessHintsScreen()
    }
    @Composable
    fun getDrawableResId(context: Context, countryCode: String): Int {
        return context.resources.getIdentifier(countryCode.lowercase(Locale.ROOT), "drawable", context.packageName)
    }

}
