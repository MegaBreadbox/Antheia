package com.mega_breadbox.antheia_plant_manager.screens.account_settings_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.CloudService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.UserModel
import com.mega_breadbox.antheia_plant_manager.nav_routes.AccountSettingsEdit
import com.mega_breadbox.antheia_plant_manager.screens.account_settings.util.AccountDetail
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.auth
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.util.ComposeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val accountService: AccountService,
    private val cloudService: CloudService,
    private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    val userDetail = savedStateHandle.toRoute<AccountSettingsEdit>().accountDetail

    var newAccountText by mutableStateOf("")
        private set
    var confirmNewAccountText by mutableStateOf("")
        private set

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateWelcomeText(input: Char) {
        _uiState.update {
            it.copy(welcomeText = it.welcomeText + input)
        }
    }

    private fun updateError(errorTextId: Int) {
        _uiState.update {
            it.copy(errorText = ComposeText(errorTextId))
        }
    }

    fun updateAccountInfoText(input: String) {
        newAccountText = input
    }

    fun updateConfirmAccountInfoText(input: String) {
        confirmNewAccountText = input
    }

    fun saveAccountInfo(navigateBack: () -> Unit) {
        if (newAccountText == confirmNewAccountText) {
            when (userDetail) {
                AccountDetail.USERNAME -> {
                    viewModelScope.launch() {
                        try {
                            accountService.updateUsername(newAccountText)
                            cloudService.updateUser(
                                UserModel(
                                    username = newAccountText,
                                    email = Firebase.auth.currentUser?.email ?: "",
                                    uid = Firebase.auth.currentUser?.uid ?: "",
                                )
                            )
                            navigateBack()
                        } catch (e: FirebaseNetworkException) {
                            updateError(R.string.not_connected_to_network)
                        }
                    }
                }

                AccountDetail.EMAIL -> {
                    viewModelScope.launch() {
                        try {
                            accountService.updateEmail(newAccountText)
                            cloudService.updateUser(
                                UserModel(
                                    username = Firebase.auth.currentUser?.displayName ?: "",
                                    email = newAccountText,
                                    uid = Firebase.auth.currentUser?.uid ?: "",
                                )
                            )
                            navigateBack()
                        }
                        catch (e: FirebaseNetworkException) {
                            updateError(R.string.not_connected_to_network)
                        }
                        catch (e: FirebaseException) {
                            updateError(R.string.error_not_a_valid_email)
                        }
                    }
                }
            }
        } else {
            updateError(R.string.credential_does_not_match)
        }
    }




}

data class UiState(
    val welcomeText: String = "",
    val errorText: ComposeText? = null
)