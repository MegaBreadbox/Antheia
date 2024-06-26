package com.example.antheia_plant_manager.screens.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.antheia_plant_manager.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {
    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState = _uiState.asStateFlow()

    var emailText by mutableStateOf("")
        private set

    var passwordText by mutableStateOf("")
        private set

    fun updateWelcomeText(input: Char) {
        _uiState.update {
            it.copy(processWelcomeText = it.processWelcomeText + input)
        }
    }
    fun updateIsPasswordVisible() {
        _uiState.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    fun updateEmail(input: String) {
        emailText = input
    }

    fun updatePassword(input: String) {
        passwordText = input
    }


}


data class WelcomeUiState(
    val processWelcomeText: String = "",
    val isPasswordVisible: Boolean = false
)