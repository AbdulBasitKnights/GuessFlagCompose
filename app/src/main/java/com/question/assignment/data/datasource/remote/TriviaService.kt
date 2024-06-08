package com.question.assignment.data.datasource.remote

import android.util.Log
import com.question.assignment.common.Constants
import com.question.assignment.data.datasource.remote.dto.QuestionResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.*


class TriviaService(
    private val client: HttpClient
) {
    suspend fun getQuestions(): List<QuestionResponse> {
        return try {
            client.get { url(Constants.base_url) }
        } catch(e: RedirectResponseException) {
            // 3xx - responses
            Log.d("Error", e.response.status.description)
            emptyList()
        } catch(e: ClientRequestException) {
            // 4xx - responses
            Log.d("Error", e.response.status.description)

            emptyList()
        } catch(e: ServerResponseException) {
            // 5xx - responses
            Log.d("Error", e.response.status.description)
            emptyList()
        } catch(e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }


}