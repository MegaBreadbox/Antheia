package com.example.antheia_plant_manager.screens.sign_in

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

    fun updateWelcomeText(input: Char) {
        _uiState.update {
            it.copy(processWelcomeText = it.processWelcomeText + input)
        }
    }


}

data class WelcomeUiState(
    val processWelcomeText: String = ""
)