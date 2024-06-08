package com.question.assignment.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = MaroonDark,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun QuestionAssignmentTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}