package com.question.assignment.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionResponse(
    val id: String,
    val countryName: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>)
