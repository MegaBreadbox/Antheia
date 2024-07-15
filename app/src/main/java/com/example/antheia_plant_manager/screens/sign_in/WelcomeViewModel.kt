package com.example.antheia_plant_manager.screens.sign_in

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.service.AccountService
import com.example.antheia_plant_manager.model.service.GoogleSignIn
import com.example.antheia_plant_manager.util.ErrorText
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val googleSignIn: Lazy<GoogleSignIn>
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

    fun signIn() {
        viewModelScope.launch {
            try {
                accountService.signIn(
                    email = emailText,
                    password = passwordText
                )
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.d(
                    "login exception",
                    "$e"
                )
                _uiState.update {
                    it.copy(
                        errorText = ErrorText(
                            signInErrorTextId = R.string.wrong_user_details_error,
                        )
                    )
                }
            }
        }
    }
    fun getGoogleSignInRequest(): GetCredentialRequest {
        return googleSignIn.get().getCredentialRequest()
    }

    fun googleSignIn(idToken: String) {
        viewModelScope.launch {
            accountService.googleSignIn(idToken)
        }
    }

    fun anonymousSignIn() {
        viewModelScope.launch {
            try {
                accountService.anonymousSignIn()
            } catch (e: Exception) {
                Log.d("login exception", "$e")
            }
        }
    }


}


data class WelcomeUiState(
    val processWelcomeText: String = "",
    val isPasswordVisible: Boolean = false,
    val errorText: ErrorText? = null
)