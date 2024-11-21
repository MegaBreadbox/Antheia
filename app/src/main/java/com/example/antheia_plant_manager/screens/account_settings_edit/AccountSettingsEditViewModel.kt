package com.example.antheia_plant_manager.screens.account_settings_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.example.antheia_plant_manager.nav_routes.AccountSettingsEdit
import com.example.antheia_plant_manager.screens.account_settings.util.AccountDetail
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

    private fun updateError(isErrorPresent: Boolean) {
        _uiState.update {
            it.copy(errorPresent = isErrorPresent)
        }
    }

    fun updateAccountInfoText(input: String) {
        newAccountText = input
    }

    fun updateConfirmAccountInfoText(input: String) {
        confirmNewAccountText = input
    }

    fun saveAccountInfo() {
        if(newAccountText == confirmNewAccountText) {
            updateError(false)
            when (userDetail) {
                AccountDetail.USERNAME -> {
                    viewModelScope.launch() {
                        accountService.updateUsername(newAccountText)
                    }
                }
                AccountDetail.EMAIL -> {
                    viewModelScope.launch() {
                        accountService.updateEmail(newAccountText)
                    }
                }
            }
        } else {
            updateError(true)
        }
    }




}

data class UiState(
    val welcomeText: String = "",
    val errorPresent: Boolean = false
)