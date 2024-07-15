package com.example.antheia_plant_manager.screens.create_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState = _uiState.asStateFlow()

    fun changeWelcomeText(input: Char) {
        _uiState.update {
            it.copy(welcomeText = it.welcomeText + input)
        }
    }
}

data class CreateAccountUiState(
    val welcomeText: String = ""
)