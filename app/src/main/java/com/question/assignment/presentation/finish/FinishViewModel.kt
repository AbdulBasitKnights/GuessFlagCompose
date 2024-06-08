package com.question.assignment.presentation.finish

import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.question.assignment.presentation.util.Screen
import com.question.assignment.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FinishViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {
    private val _points = mutableIntStateOf(0)
    val points: State<Int> = _points

    init {
        viewModelScope.launch {
            scoreRepository.getTempPoints.collect {
                _points.intValue = it
            }
        }
    }

    fun onEvent(event: FinishEvent) {
        when (event) {
            is FinishEvent.PlayAgain -> {
                event.navHostController.navigate(Screen.QuestionScreen.route) {
                    popUpTo(event.navHostController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                }
                viewModelScope.launch {
                    scoreRepository.clearTempPoints()
                }
            }
            is FinishEvent.Share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Hi, I scored ${_points.value} in Quizella created by Gautam Hazarika.")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                event.context.startActivity(shareIntent)
            }
            is FinishEvent.GoHome -> {
                event.navHostController.navigate(Screen.MainScreen.route) {
                    popUpTo(event.navHostController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                }

                viewModelScope.launch {
                    scoreRepository.clearTempPoints()
                }
            }
        }
    }

}