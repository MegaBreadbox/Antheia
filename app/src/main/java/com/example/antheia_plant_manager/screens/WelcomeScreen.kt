package com.example.antheia_plant_manager.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = hiltViewModel<WelcomeViewModel>()
) {
    Column(){
        val welcome = "Welcome\nto\nAntheia"
        var processWelcome by remember { mutableStateOf("") }
        LaunchedEffect(key1 = processWelcome) {
            if(welcome.length != processWelcome.length) {
                delay(100L)
                processWelcome += welcome[processWelcome.length]
            }
        }
        Text(
            text = processWelcome,
            style = MaterialTheme.typography.displayLarge
        )
    }
}