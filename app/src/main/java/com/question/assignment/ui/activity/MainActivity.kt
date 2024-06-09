package com.question.assignment.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.ui.theme.QuestionAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            QuestionAssignmentTheme{
                CountryFlagsApp()
            }
        }
    }

}
@Composable
fun CountryFlagsApp() {
    val navController = rememberNavController()
    val sharedViewModel: CountryViewModel = viewModel()
    NavHost(navController = navController, startDestination = "mainMenu"){
        composable("mainMenu") { MainMenuScreen(sharedViewModel)}
    }
}