package com.question.assignment.ui.activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun MainMenuScreen(navController: NavController) {
    val context = LocalContext.current
    val startGuessHintsActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            context.startActivity(Intent(context,GuessFlagActivity::class.java)) }) {
            Text("Guess the Country")
        }
        Button(onClick = { navController.navigate("GuessHintScreen") }) {
            Text("Guess-Hints")
        }
        Button(onClick = { navController.navigate("GuessTheFlagScreen") }) {
            Text("Guess the Flag")
        }
        Button(onClick = { navController.navigate("AdvancedLevelScreen") }) {
            Text("Advanced Level")
        }

    }
}

