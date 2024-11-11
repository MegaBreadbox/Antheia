package com.example.antheia_plant_manager.screens.account_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    fun signOut() {
        viewModelScope.launch(coroutineDispatcher) {
            accountService.signOutOfApp()
        }
    }

    fun passwordReset() {
        viewModelScope.launch(coroutineDispatcher) {
            accountService.sendPasswordReset()
        }
    }
}