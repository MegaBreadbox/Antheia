package com.example.antheia_plant_manager.screens.account_settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState = _dialogState.asStateFlow()

    val currentUser = accountService.currentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun signOut(navigation: () -> Unit) {
        viewModelScope.launch() {
            _dialogState.update {
                it.copy(isEnabled = false)
            }
            accountService.signOutOfApp()
            navigation()
        }
    }

    fun deleteAccount(navigation: () -> Unit) {
        viewModelScope.launch() {
            _dialogState.update {
                it.copy(isEnabled = false)
            }
            accountService.deleteAccount()
            navigation()
        }
    }

    fun updateDialogState(
        dialogState: DialogState,
    ) {
        _dialogState.update {
            it.copy(
                iconResource = dialogState.iconResource,
                title = dialogState.title,
                text = dialogState.text,
                dialogAction = dialogState.dialogAction,
                isEnabled = dialogState.isEnabled,
            )
        }
    }

    fun passwordReset() {
        viewModelScope.launch(coroutineDispatcher) {
            accountService.sendPasswordReset()
        }
    }
}

data class DialogState(
    @DrawableRes val iconResource: Int? = null,
    @StringRes val title: Int = 0,
    @StringRes val text: Int = 0,
    val dialogAction: () -> Unit = { },
    val isEnabled: Boolean = false,
)