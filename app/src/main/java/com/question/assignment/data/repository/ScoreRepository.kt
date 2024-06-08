package com.question.assignment.data.repository

import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    suspend fun setPoints(points: Int)
    val getPoints : Flow<Int>
    val getTempPoints : Flow<Int>
    suspend fun setTempPoints(points: Int)
    suspend fun clearTempPoints()

}