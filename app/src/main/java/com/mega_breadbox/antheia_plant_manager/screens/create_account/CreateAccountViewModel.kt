package com.mega_breadbox.antheia_plant_manager.screens.create_account

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.GoogleSignIn
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.CloudService
import com.mega_breadbox.antheia_plant_manager.util.ComposeText
import com.mega_breadbox.antheia_plant_manager.util.validatePassword
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val accountService: AccountService,
    private val googleSignIn: Lazy<GoogleSignIn>,
    private val cloudService: CloudService,
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

    fun updateErrorText(resourceId: Int) {
        _uiState.update {
            it.copy(
                errorText = ComposeText(
                    textId = resourceId
                )
            )
        }
    }

    fun getGoogleSignInRequest(): GetCredentialRequest {
        return googleSignIn.get().getCredentialRequest()
    }

    fun googleSignIn(googleResponse: GetCredentialResponse, navigate:() -> Unit) {
        viewModelScope.launch {
            try {
                if (googleResponse.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    if(Firebase.auth.currentUser?.isAnonymous == true) {
                        accountService.googleLinkAccount(GoogleIdTokenCredential.createFrom(googleResponse.credential.data).idToken)
                    } else {
                        accountService.googleSignIn(GoogleIdTokenCredential.createFrom(googleResponse.credential.data).idToken)
                    }
                    cloudService.addUser()
                    navigate()
                }
            } catch (e: Exception) {
                Log.d("CreateAccountViewModel", "googleSignIn: $e")
                updateErrorText(R.string.error_occurred_while_signing_in)
            }
        }
    }

    fun createAccount(navigate: () -> Unit) {
        when {
            !passwordText.validatePassword() -> updateErrorText(R.string.password_requirements_error)
            passwordText != confirmPasswordText -> updateErrorText(R.string.passwords_do_not_match_error)
            else -> {
                viewModelScope.launch() {
                    try {
                        if(Firebase.auth.currentUser?.isAnonymous == true)
                        {
                            accountService.emailLinkAccount(emailText, passwordText)
                        } else {
                            accountService.createAccount(
                                emailText,
                                passwordText
                            )
                        }
                        cloudService.addUser()
                        navigate()
                    } catch (e: Exception) {
                        println("$e ${e.message}")
                        when (e) {
                            is IllegalArgumentException -> updateErrorText(R.string.create_account_empty_field_error)
                            is FirebaseAuthInvalidCredentialsException -> updateErrorText(R.string.create_account_invalid_email_error)
                            is FirebaseAuthUserCollisionException -> updateErrorText(R.string.this_email_is_already_in_use)
                        }
                    }
                }
            }
        }
    }
}

data class CreateAccountUiState(
    val welcomeText: String = "",
    val errorText: ComposeText? = null,
    val isPasswordVisible: Boolean = false,

)