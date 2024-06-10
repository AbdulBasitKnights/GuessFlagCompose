package com.question.assignment.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

class AdvancedLevelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
                AdvancedLevelScreen()
        }

    }
    @Composable
    fun AdvancedLevelScreen() {
        val viewModel: CountryViewModel = viewModel()
        val countries by viewModel.countries.collectAsState(initial = emptyList())
        var flagCountries by remember { mutableStateOf(listOf<Country>()) }
        var userInputs by remember { mutableStateOf(listOf(TextFieldValue(), TextFieldValue(), TextFieldValue())) }
        var incorrectAttempts by remember { mutableStateOf(0) }
        var resultMessage by remember { mutableStateOf("") }
        var resultColor by remember { mutableStateOf(Color.Transparent) }
        var buttonText by remember { mutableStateOf("Submit") }
        var score by remember { mutableIntStateOf(0) }

        fun setNewRound() {
            if (countries.isNotEmpty()) {
                flagCountries = countries.shuffled().take(3)
                userInputs = listOf(TextFieldValue(), TextFieldValue(), TextFieldValue())
                incorrectAttempts = 0
                resultMessage = ""
                resultColor = Color.Transparent
                buttonText = "Submit"
            }
        }

        LaunchedEffect(countries) {
            if (countries.isNotEmpty() && flagCountries.isEmpty()) {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Score: $score", fontSize = 20.sp, color = Color.Black)
                }

                flagCountries.forEachIndexed { index, country ->
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
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                flagCountries.forEachIndexed { index, _ ->
                    TextField(
                        value = userInputs[index],
                        onValueChange = {
                            userInputs = userInputs.toMutableList().apply { set(index, it) }
                        },
                        enabled = buttonText == "Submit",
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = if (resultMessage.isNotEmpty()) {
                                if (flagCountries[index].name.equals(
                                        userInputs[index].text,
                                        ignoreCase = true
                                    )
                                ) Color.Green else Color.Red
                            } else Color.White,
                            textColor = if (resultMessage.isNotEmpty() && !flagCountries[index].name.equals(
                                    userInputs[index].text,
                                    ignoreCase = true
                                )
                            ) Color.Red else Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (buttonText == "Submit") {
                            val correctGuesses = flagCountries.mapIndexed { index, country ->
                                country.name.equals(userInputs[index].text, ignoreCase = true)
                            }
                            if (correctGuesses.all { it }) {
                                resultMessage = "CORRECT!"
                                resultColor = Color.Green
                                score += 3
                                buttonText = "Next"
                            } else {
                                resultMessage = "WRONG! Try again."
                                resultColor = Color.Red
                                incorrectAttempts++
                                if (incorrectAttempts >= 3) {
                                    resultMessage =
                                        "WRONG! Correct answers: ${flagCountries.joinToString(", ") { it.name }}"
                                    buttonText = "Next"
                                }
                            }
                        } else {
                            setNewRound()
                        }
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

    @Composable
    fun getDrawableResId(context: Context, countryCode: String): Int {
        return context.resources.getIdentifier(countryCode.lowercase(Locale.ROOT), "drawable", context.packageName)
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewAdvancedLevelScreen() {
        AdvancedLevelScreen()
    }
}