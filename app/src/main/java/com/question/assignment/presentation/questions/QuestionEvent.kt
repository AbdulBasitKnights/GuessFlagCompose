package com.question.assignment.presentation.questions

import androidx.navigation.NavHostController

sealed class QuestionEvent {
    data class NextQuestion(val navHostController: NavHostController) : QuestionEvent()
    data class ChangeAnswer(val ans: String) : QuestionEvent()

    data object OpenDialog : QuestionEvent()
    data object CloseDialog : QuestionEvent()


}