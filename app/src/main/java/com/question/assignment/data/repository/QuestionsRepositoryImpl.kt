package com.question.assignment.data.repository

import com.question.assignment.data.datasource.local.QuestionCacheDao
import com.question.assignment.data.datasource.remote.TriviaService
import com.question.assignment.ui.model.Question
import kotlinx.coroutines.flow.Flow

class QuestionsRepositoryImpl(
    private val dao: QuestionCacheDao,
    private val triviaService: TriviaService
) : QuestionsRepository {

    override suspend fun getQuestions(): Flow<List<Question>> {

        triviaService.getQuestions().forEach {
            dao.insertQuestion(
                Question(
                    id = it.id,
                    category = it.category,
                    correctAnswer = it.correctAnswer,
                    answers = (it.incorrectAnswers + it.correctAnswer).shuffled(),
                    question = it.question,
                    tags = it.tags,
                    type = it.type,
                    difficulty = it.difficulty,
                    regions = it.regions,
                    isNiche = it.isNiche
                )
            )

        }
        return dao.getQuestions()
    }


    override suspend fun clearQuestions() {
        dao.clear()
    }
}