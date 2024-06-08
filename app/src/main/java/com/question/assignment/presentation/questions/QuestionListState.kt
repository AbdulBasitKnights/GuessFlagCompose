package com.question.assignment.presentation.questions

import com.question.assignment.ui.model.Question


data class QuestionListState(
    val list: MutableList<Question> = mutableListOf(),
    val tempPoints: Int = 0,
    val points: Int = 0
)
