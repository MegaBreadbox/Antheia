package com.example.antheia_plant_manager.screens.account_settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.model.service.firebase_auth.AccountService
import com.example.antheia_plant_manager.model.service.firestore.CloudService
import com.example.antheia_plant_manager.model.service.firestore.UserModel
import com.example.antheia_plant_manager.util.SUBSCRIBE_DELAY
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val cloudService: CloudService,
    private val plantDatabase: PlantRepository,
    private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState = _dialogState.asStateFlow()

    val userState = cloudService.userFlow()
        .catch { e -> if( e is FirebaseFirestoreException) emit(UserModel()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBE_DELAY),
            initialValue = null
        )

    fun signOut(navigation: () -> Unit) {
        viewModelScope.launch() {
            _dialogState.update {
                it.copy(isEnabled = false)
            }
            plantDatabase.deleteUserData(accountService.currentUserId)
            accountService.signOutOfApp()
            navigation()
        }
    }

    fun signInProvider(): String? {
        return if(Firebase.auth.currentUser?.isAnonymous == true) {
            null
        } else {
            Firebase.auth.currentUser?.providerData?.get(1)?.providerId?: ""
        }
    }

    fun deleteAccount(navigation: () -> Unit, anonymousNavigation: () -> Unit) {
        viewModelScope.launch() {
            _dialogState.update {
                it.copy(isEnabled = false)
            }
            if(Firebase.auth.currentUser?.isAnonymous == true) {
                accountService.signOutOfApp()

                anonymousNavigation()
            } else {
                navigation()
            }
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