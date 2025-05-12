package com.mega_breadbox.antheia_plant_manager.screens.account_settings_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.screens.account_settings.ConfirmationDialog
import com.mega_breadbox.antheia_plant_manager.screens.account_settings.util.AccountDetail
import com.mega_breadbox.antheia_plant_manager.screens.account_settings.util.DialogState
import com.mega_breadbox.antheia_plant_manager.util.WelcomeTextCompact
import com.mega_breadbox.antheia_plant_manager.ui.theme.AntheiaplantmanagerTheme

@Composable
fun AccountSettingsEditScreen(
    navigateBack: () -> Unit,
    viewModel: AccountSettingsEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    EditFormCompact(
        dialogState = dialogState,
        accountInfoType = viewModel.userDetail,
        headerText = uiState.welcomeText,
        updateHeaderText = { viewModel.updateWelcomeText(it) },
        newAccountDetail = viewModel.newAccountText,
        errorText = uiState.errorText?.asString(),
        confirmNewAccountDetail = viewModel.confirmNewAccountText,
        updateNewAccountDetail = { viewModel.updateAccountInfoText(it) },
        saveChanges = { viewModel.saveAccountInfo(navigateBack) },
        updateDialogState = { viewModel.updateDialogState(it) },
        updateConfirmNewAccountDetail = { viewModel.updateConfirmAccountInfoText(it) },
    )
}

@Composable
fun EditFormCompact(
    dialogState: DialogState,
    accountInfoType: AccountDetail,
    headerText: String,
    updateHeaderText: (Char) -> Unit,
    newAccountDetail: String,
    confirmNewAccountDetail: String,
    errorText: String?,
    saveChanges: () -> Unit,
    updateNewAccountDetail: (String) -> Unit,
    updateDialogState: (DialogState) -> Unit,
    updateConfirmNewAccountDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if(dialogState.isEnabled) {
            ConfirmationDialog(
                dialogTitleResource = dialogState.title,
                dialogTextResource = dialogState.text,
                onDismissRequest = { updateDialogState(dialogState.copy(isEnabled = false)) },
                onConfirmClick = dialogState.dialogAction,
                onDismissClick = { updateDialogState(dialogState.copy(isEnabled = false)) }
            )
        }
        WelcomeTextCompact(
            processWelcomeText = headerText,
            updateWelcomeText = { updateHeaderText(it) },
            welcomeText = stringResource(
                R.string.change_account_detail_header,
                stringResource(accountInfoType.stringId).lowercase()
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
        FormType(
            accountInfoType = accountInfoType,
            newAccountDetail = newAccountDetail,
            errorText = errorText,
            confirmNewAccountDetail = confirmNewAccountDetail,
            updateNewAccountDetail = updateNewAccountDetail,
            updateConfirmNewAccountDetail = updateConfirmNewAccountDetail,
            saveChanges = saveChanges
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
        Button(
            onClick = saveChanges,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.large_padding))
        ) {
            Text(text = stringResource(R.string.save))
        }
        Spacer(modifier = modifier
            .imePadding()
        )
    }
}

@Composable
fun FormType(
    accountInfoType: AccountDetail,
    newAccountDetail: String,
    confirmNewAccountDetail: String,
    errorText: String?,
    updateNewAccountDetail: (String) -> Unit,
    updateConfirmNewAccountDetail: (String) -> Unit,
    saveChanges: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.small_padding)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.medium_padding))
    ) {
        Column(
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.large_padding))
        ) {
            TextField(
                value = newAccountDetail,
                onValueChange = { updateNewAccountDetail(it) },
                placeholder = { Text(text = stringResource(accountInfoType.stringId)) },
                isError = errorText != null,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent

                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (accountInfoType == AccountDetail.EMAIL) KeyboardType.Email else KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = modifier
                    .width(dimensionResource(id = R.dimen.textfield_size_compact))
            )
            Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
            TextField(
                value = confirmNewAccountDetail,
                onValueChange = { updateConfirmNewAccountDetail(it) },
                isError = errorText != null,
                placeholder = {
                    Text(
                        text = stringResource(
                            R.string.confirm_text_form,
                            stringResource(accountInfoType.stringId).lowercase()
                        )
                    )
                },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent

                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (accountInfoType == AccountDetail.EMAIL) KeyboardType.Email else KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { saveChanges() }),
                supportingText = { Text(errorText ?: "") },
                modifier = modifier
                    .width(dimensionResource(id = R.dimen.textfield_size_compact))
            )
        }
    }
}

@Composable
@Preview
fun EditFormCompactPreview() {
    AntheiaplantmanagerTheme() {
        EditFormCompact(
            dialogState = DialogState(),
            accountInfoType = AccountDetail.USERNAME,
            updateNewAccountDetail = { },
            headerText = "test",
            updateHeaderText = { },
            newAccountDetail = "",
            confirmNewAccountDetail = "",
            updateDialogState = { },
            updateConfirmNewAccountDetail = { },
            errorText = null,
            saveChanges = { },
        )
    }
}