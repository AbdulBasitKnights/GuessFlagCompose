package com.question.assignment.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.question.assignment.presentation.util.AppNavHost
import com.question.assignment.ui.theme.QuestionAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuestionAssignmentTheme{
            val navController = rememberNavController()
                AppNavHost(navController,context=this)
            }
        }
    }
}