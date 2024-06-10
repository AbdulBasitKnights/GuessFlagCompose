package com.question.assignment.ui.activity

import com.question.assignment.R
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.question.assignment.presentation.common.CountryViewModel

@Composable
fun MainMenuScreen(sharedViewModel: CountryViewModel) {
    val context = LocalContext.current

    // Background drawable
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    context.startActivity(Intent(context, GuessFlagActivity::class.java))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    "Guess the Country",
                    color = Color.Blue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {
                    context.startActivity(Intent(context, GuessHintsActivity::class.java))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    "Guess-Hints",
                    color = Color.Blue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {
                    context.startActivity(Intent(context, GuessCountryActivity::class.java))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    "Guess the Flag",
                    color = Color.Blue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {
                    context.startActivity(Intent(context, AdvancedLevelActivity::class.java))
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    "Advanced Level",
                    color = Color.Blue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



