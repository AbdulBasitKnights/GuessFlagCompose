package com.question.assignment.presentation.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.question.assignment.R
import com.question.assignment.ui.activity.AdvancedLevelScreen
import com.question.assignment.ui.activity.GuessHintsScreen
import com.question.assignment.ui.activity.GuessTheCountryScreen
import com.question.assignment.ui.activity.GuessTheFlagScreen
import com.question.assignment.ui.activity.MainMenuScreen
import com.question.assignment.ui.activity.ResultScreen
import com.question.assignment.ui.model.CountryFlag

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu_screen")
    object GuessTheCountry : Screen("guess_the_country_screen")
    object GuessHints : Screen("guess_hint_screen")
    object GuessTheFlag : Screen("guess_the_flag")
    object AdvancedLevel : Screen("advanced_level_screen")
    object Result : Screen("result/{isCorrect}/{countryName}") {
        fun createRoute(isCorrect: Boolean, countryName: String) = "result/$isCorrect/$countryName"
    }
}
