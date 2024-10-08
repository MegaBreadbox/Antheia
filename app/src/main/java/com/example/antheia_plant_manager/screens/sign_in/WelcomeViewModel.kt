package com.example.antheia_plant_manager.screens.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.model.service.AccountService
import com.example.antheia_plant_manager.model.service.GoogleSignIn
import com.example.antheia_plant_manager.util.ComposeText
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuthException
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

    fun updateErrorText(errorStringId: Int ) {
        _uiState.update {
            it.copy(
                errorText = ComposeText(
                    textId = errorStringId
                )
            )
        }
    }

    fun signIn(navigate: () -> Unit) {
        viewModelScope.launch {
            try {
                accountService.signIn(
                    email = emailText,
                    password = passwordText
                )
                navigate()
            } catch(e: Exception) {
                when (e) {
                    is FirebaseAuthInvalidCredentialsException -> updateErrorText(R.string.wrong_user_details_error)
                    is FirebaseAuthException -> updateErrorText(R.string.error_occurred_while_signing_in)
                    is IllegalArgumentException -> updateErrorText(R.string.empty_credentials_error)
                    is NullPointerException -> updateErrorText(R.string.empty_credentials_error)
                }
            }
        }
    }
    fun getGoogleSignInRequest(): GetCredentialRequest {
        return googleSignIn.get().getCredentialRequest()
    }

    fun googleSignIn(googleResponse: GetCredentialResponse, navigate:() -> Unit) {
        viewModelScope.launch {
            try {
                if (googleResponse.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    accountService.googleSignIn(GoogleIdTokenCredential.createFrom(googleResponse.credential.data).idToken)
                    navigate()
                }
            } catch (e: Exception) {
                updateErrorText(R.string.error_occurred_while_signing_in)
            }
        }
    }

    fun anonymousSignIn(navigate: () -> Unit) {
        viewModelScope.launch {
            try {
                accountService.anonymousSignIn()
                navigate()
            } catch (e: Exception) {
                updateErrorText(R.string.error_occurred_while_signing_in)
            }
        }
    }


}


data class WelcomeUiState(
    val processWelcomeText: String = "",
    val isPasswordVisible: Boolean = false,
    val errorText: ComposeText? = null
)