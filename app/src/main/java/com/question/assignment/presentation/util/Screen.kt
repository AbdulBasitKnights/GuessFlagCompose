package com.question.assignment.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.question.assignment.R
import com.question.assignment.data.repository.CountryRepository
import com.question.assignment.ui.activity.AdvancedLevelScreen
import com.question.assignment.ui.activity.GuessHintsScreen
import com.question.assignment.ui.activity.GuessTheCountryScreen
import com.question.assignment.ui.activity.MainMenuScreen
import com.question.assignment.ui.activity.ResultScreen
import com.question.assignment.ui.model.CountryFlag

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object GuessTheCountry : Screen("guess_the_country")
    object GuessHints : Screen("guess_hints")
    object GuessTheFlag : Screen("guess_the_flag")
    object AdvancedLevel : Screen("advanced_level")
    object Result : Screen("result/{isCorrect}/{countryName}") {
        fun createRoute(isCorrect: Boolean, countryName: String) = "result/$isCorrect/$countryName"
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.MainMenu.route) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen { gameMode ->
                when (gameMode) {
                    "GuessTheCountry" -> navController.navigate(Screen.GuessTheCountry.route)
                    "GuessHints" -> navController.navigate(Screen.GuessHints.route)
                    "GuessTheFlag" -> navController.navigate(Screen.GuessTheFlag.route)
                    "AdvancedLevel" -> navController.navigate(Screen.AdvancedLevel.route)
                }
            }
        }
        composable(Screen.GuessTheCountry.route) {
            val countries = CountryRepository.getCountries()
            GuessTheCountryScreen(countries) { isCorrect, country ->
                navController.navigate(Screen.Result.createRoute(isCorrect, country.flagName))
            }
        }
        composable(Screen.GuessHints.route) {
            val randomCountry = CountryRepository.getRandomCountry()
            GuessHintsScreen(randomCountry) { isCorrect, country ->
                navController.navigate(Screen.Result.createRoute(isCorrect, country.flagName))
            }
        }
        composable(Screen.GuessTheFlag.route) {
            val countries = CountryRepository.getCountries()
            GuessTheCountryScreen(countries) { isCorrect, country ->
                navController.navigate(Screen.Result.createRoute(isCorrect, country.flagName))
            }
        }
        composable(Screen.AdvancedLevel.route) {
            val countries = CountryRepository.getCountries()
            AdvancedLevelScreen(countries) { isCorrect, countryList ->
                navController.navigate(Screen.Result.createRoute(isCorrect, countryList.joinToString(", ") { it.flagName }))
            }
        }
        composable(Screen.Result.route) { backStackEntry ->
            val isCorrect = backStackEntry.arguments?.getBoolean("isCorrect") ?: false
            val countryName = backStackEntry.arguments?.getString("countryName") ?: ""
            ResultScreen(isCorrect, CountryFlag(R.drawable.ic_launcher_background, countryName)) {
                navController.navigate(Screen.MainMenu.route) {
                    popUpTo(Screen.MainMenu.route) { inclusive = true }
                }
            }
        }
    }
}
