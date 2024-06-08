package com.question.assignment.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.question.assignment.data.datasource.local.QuestionsCacheDB
import com.question.assignment.data.datasource.remote.TriviaService
import com.question.assignment.data.repository.QuestionsRepository
import com.question.assignment.data.repository.QuestionsRepositoryImpl
import com.question.assignment.data.repository.ScoreRepository
import com.question.assignment.data.repository.ScoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Singleton
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesQuestionsCacheDB(app: Application): QuestionsCacheDB {
        return Room.databaseBuilder(
            app,
            QuestionsCacheDB::class.java,
            QuestionsCacheDB.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun providesScoreRepository(
        @ApplicationContext context: Context
    ): ScoreRepository = ScoreRepositoryImpl(context = context)

    @Provides
    @Singleton
    fun providesHttpClient() = HttpClient(Android) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("NetworkMessage", "log: $message")
                }
            }
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    @Provides
    @Singleton
    fun providesTriviaService(client: HttpClient) = TriviaService(client)


    @Provides
    @Singleton
    fun providesQuestionsRepository(
        db: QuestionsCacheDB,
        triviaService: TriviaService
    ): QuestionsRepository =
        QuestionsRepositoryImpl(db.questionCacheDao, triviaService)
}