package com.example.antheia_plant_manager.screens.create_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.service.AccountService
import com.example.antheia_plant_manager.util.ErrorText
import com.example.antheia_plant_manager.util.validatePassword
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val accountService: AccountService,
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState = _uiState.asStateFlow()

    var emailText by mutableStateOf("")
        private set

    var passwordText by mutableStateOf("")
        private set

    var confirmPasswordText by mutableStateOf("")
        private set

    fun changeWelcomeText(input: Char) {
        _uiState.update {
            it.copy(welcomeText = it.welcomeText + input)
        }
    }

    fun updateEmailText(input: String) {
        emailText = input
    }

    fun updatePasswordText(input: String) {
        passwordText = input
    }

    fun updateConfirmPasswordText(input: String) {
        confirmPasswordText = input
    }

    fun updateIsPasswordVisible() {
        _uiState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun updateErrorText(resourceId: Int) {
        _uiState.update {
            it.copy(
                errorText = ErrorText(
                    signInErrorTextId = resourceId
                )
            )
        }
    }

    fun createAccount() {
        when {
            !passwordText.validatePassword() -> updateErrorText(R.string.password_requirements_error)
            passwordText != confirmPasswordText -> updateErrorText(R.string.passwords_do_not_match_error)
            else -> {
                viewModelScope.launch() {
                    try {
                        accountService.createAccount(
                            emailText,
                            passwordText
                        )
                    } catch (e: Exception) {
                        println("$e ${e.message}")
                        when (e) {
                            is IllegalArgumentException -> updateErrorText(R.string.create_account_empty_field_error)
                            is FirebaseAuthInvalidCredentialsException -> updateErrorText(R.string.create_Account_invalid_email_error)
                        }
                    }
                }
            }
        }
    }
}

data class CreateAccountUiState(
    val welcomeText: String = "",
    val errorText: ErrorText? = null,
    val isPasswordVisible: Boolean = false,

)