package com.question.assignment.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.question.assignment.presentation.common.CountryViewModel
import com.question.assignment.ui.model.Country
import com.question.assignment.ui.model.CountryFlag

@Composable
fun GuessTheCountryScreen(navController: NavController, countries: List<Country>?= emptyList()) {
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    val randomCountry = remember { countries?.random() }
    val viewModel: CountryViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadCountries(context)
    }
    val countries by viewModel.countries.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/hjnilsson/country-flags/master/png1000px/${randomCountry?.name?.lowercase()}.png"),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        DropdownMenu(
            expanded = true,
            onDismissRequest = { /*TODO*/ },
        ) {
            countries?.forEach { country ->
                DropdownMenuItem(onClick = { selectedCountry = country }) {
                    Text(country.name)
                }
            }
        }
    }
    if (countries.isNotEmpty()) {
        var selectedCountry by remember { mutableStateOf("") }

        BasicTextField(
            value = selectedCountry,
            onValueChange = { selectedCountry = it }
        )

        Button(onClick = { /* Submit answer */ }) {
            Text("Submit")
        }
    }
}
