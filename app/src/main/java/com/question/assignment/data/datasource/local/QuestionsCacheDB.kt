package com.question.assignment.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.question.assignment.ui.model.Question

@Database(
    entities = [Question::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QuestionsCacheDB : RoomDatabase() {
    abstract val questionCacheDao: QuestionCacheDao
    companion object{
        const val DATABASE_NAME = "questions_db"
    }
}