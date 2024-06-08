package com.question.assignment.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.question.assignment.data.repository.QuestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AppViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
) : ViewModel() {

    fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.ClearCache -> {
                viewModelScope.launch {
                    questionsRepository.clearQuestions()
                }
            }
        }
    }
}