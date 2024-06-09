package com.question.assignment.presentation.util

import com.question.assignment.ui.model.Country
import kotlinx.coroutines.flow.MutableStateFlow

var countriesData = MutableStateFlow<List<Country>>(emptyList())