package com.example.antheia_plant_manager.screens.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel<WelcomeViewModel>()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
    ){
        val welcome = stringResource(R.string.welcome_to_antheia)
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = uiState.value.processWelcomeText) {
            if(welcome.length != uiState.value.processWelcomeText.length) {
                delay(100L)
                viewModel.updateWelcomeText(welcome[uiState.value.processWelcomeText.length])
            }
        }
        Text(
            text = uiState.value.processWelcomeText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )

    }
}