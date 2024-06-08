package com.question.assignment.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.question.assignment.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {


    private val _points = mutableIntStateOf(0)
    val points: State<Int> = _points

    init {
      viewModelScope.launch  {
            scoreRepository.getPoints.collect {
                _points.intValue = it
            }
        }
    }


}