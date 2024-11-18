package com.example.antheia_plant_manager.screens.account_settings

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.account_settings.util.AccountInfoType
import com.example.antheia_plant_manager.util.Header
import com.example.compose.AntheiaplantmanagerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AccountSettingsScreen(
    navigateChangeDetail: (AccountInfoType) -> Unit,
    navigateSignIn: () -> Unit,
    viewModel: AccountSettingsViewModel = hiltViewModel(),
) {
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
    AccountSettingsScreenCompact(
        dialogState = dialogState,
        updateDialogState = { viewModel.updateDialogState(it) },
        onSignOutClick = { viewModel.signOut(navigateSignIn) },
        onUserNameClick = { navigateChangeDetail(it) },
        onEmailClick = { navigateChangeDetail(it) },
        onPasswordClick = { viewModel.passwordReset() },
        onDeleteAccountClick = { viewModel.deleteAccount(navigateSignIn) },
    )
}

@Composable
fun AccountSettingsScreenCompact(
    dialogState: DialogState,
    updateDialogState: (DialogState) -> Unit,
    onSignOutClick: () -> Unit,
    onUserNameClick: (AccountInfoType) -> Unit,
    onEmailClick: (AccountInfoType) -> Unit,
    onPasswordClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = Firebase.auth.currentUser
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .safeContentPadding()
            .fillMaxWidth()
    ) {
        Header(
            screenTitle = stringResource(id = R.string.account_settings),
        )
        if(dialogState.isEnabled) {
            ConfirmationDialog(
                dialogTitleResource = dialogState.title,
                dialogTextResource = dialogState.text,
                onDismissRequest = { updateDialogState(dialogState.copy(isEnabled = false)) },
                onConfirmClick = dialogState.dialogAction,
                onDismissClick = { updateDialogState(dialogState.copy(isEnabled = false)) }
            )
        }
        if(user?.providerData?.get(1)?.providerId != "google.com") {
            AccountDetail(
                accountDetailTitle = stringResource(R.string.username),
                accountDetail = user?.displayName?: "",
                onDetailClick = { onUserNameClick(AccountInfoType.Username(user?.displayName)) }
            )
            Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.big_padding)))
            AccountDetail(
                accountDetailTitle = stringResource(R.string.email),
                accountDetail = user?.email.toString(),
                onDetailClick = { onEmailClick(AccountInfoType.Email(user?.email?: "")) }
            )
            Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.big_padding)))
            AccountDetailMiniature(
                accountDetailTitle = stringResource(R.string.password),
                onDetailClick = { onPasswordClick() }
            )
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
        Row(
            modifier = modifier
        ) {
            FilledTonalButton(
                onClick = {
                    updateDialogState(
                        dialogState.copy(
                            title = R.string.delete_account,
                            text = R.string.delete_account_confirmation,
                            dialogAction = onDeleteAccountClick,
                            isEnabled = true
                        )
                    )
                },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(text = stringResource(R.string.delete_account))
            }
            Spacer(modifier = modifier.padding(dimensionResource(id = R.dimen.medium_padding)))
            OutlinedButton(
                onClick = {
                    updateDialogState(
                        dialogState.copy(
                            title = R.string.sign_out,
                            text = R.string.sign_out_confirmation,
                            dialogAction = onSignOutClick,
                            isEnabled = true
                        )
                    )
                },
            ) {
                Text(text = stringResource(R.string.sign_out))
            }
        }

    }
}

@Composable
fun AccountDetail(
    accountDetailTitle: String,
    accountDetail: String,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .safeContentPadding()
            .background(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape)),
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            .border(
                width = dimensionResource(id = R.dimen.small_padding),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape))
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.big_padding),
                vertical = dimensionResource(id = R.dimen.medium_padding)
            )
    ) {
        Text(
            text = accountDetailTitle,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            if (accountDetail == "") {
                Text(
                    text = stringResource(
                        R.string.account_detail_not_set,
                        accountDetailTitle
                    ),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            } else {
                Text(
                    text = accountDetail,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Spacer(modifier = modifier.weight(1f))
            TextButton(
                onClick = { onDetailClick() },
            ) {
                Text(
                    text = stringResource(
                        R.string.edit,
                        accountDetailTitle.lowercase()
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun AccountDetailMiniature(
    accountDetailTitle: String,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .safeContentPadding()
            .background(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape)),
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            .border(
                width = dimensionResource(id = R.dimen.small_padding),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_shape))
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.large_padding),
                vertical = dimensionResource(id = R.dimen.medium_padding)
            )
    ) {
        Text(
            text = accountDetailTitle,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall
        )
        TextButton(
            onClick = { onDetailClick() },
        ) {
            Text(
                text = stringResource(
                    R.string.edit,
                    accountDetailTitle.lowercase()
                ),
                textAlign = TextAlign.Center,
            )
        }

    }
}

@Composable
fun ConfirmationDialog(
    @StringRes dialogTitleResource: Int,
    @StringRes dialogTextResource: Int,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = stringResource(id = dialogTitleResource)) },
        text = { Text(text = stringResource(id = dialogTextResource)) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmClick() }) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(text = stringResource(R.string.dismiss))
            }
        },

    )
}


@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun AccountDetailPreview() {
    AntheiaplantmanagerTheme() {
        AccountDetail(
            accountDetailTitle = "Detail",
            accountDetail = "current Detail",
            onDetailClick = {}
        )
    }
}
@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun AccountSettingsScreenCompactPreview() {
    AntheiaplantmanagerTheme() {
        AccountSettingsScreenCompact(
            dialogState = DialogState(),
            updateDialogState = { },
            onSignOutClick = { },
            onUserNameClick = { },
            onEmailClick = { },
            onPasswordClick = { },
            onDeleteAccountClick = { },
        )
    }
}