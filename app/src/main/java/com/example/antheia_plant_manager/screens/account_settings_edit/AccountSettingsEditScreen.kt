package com.example.antheia_plant_manager.screens.account_settings_edit

import android.util.Log
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
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.account_settings.util.AccountDetail
import com.example.antheia_plant_manager.screens.sign_in.WelcomeTextCompact
import com.example.compose.AntheiaplantmanagerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun AccountSettingsEditScreen(
    navigateBack: () -> Unit,
    viewModel: AccountSettingsEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    EditFormCompact(
        accountInfoType = viewModel.userDetail,
        headerText = uiState.welcomeText,
        updateHeaderText = { viewModel.updateWelcomeText(it) },
        newAccountDetail = viewModel.newAccountText,
        errorPresent = uiState.errorPresent,
        confirmNewAccountDetail = viewModel.confirmNewAccountText,
        updateNewAccountDetail = { viewModel.updateAccountInfoText(it) },
        saveChanges = {
            coroutineScope.launch {
                Log.d("before change  ", Firebase.auth.currentUser?.displayName?: "")
                viewModel.saveAccountInfo()
                Log.d("after change  ", Firebase.auth.currentUser?.displayName?: "")
                navigateBack()
            }
        },
        updateConfirmNewAccountDetail = { viewModel.updateConfirmAccountInfoText(it) },
    )
}

@Composable
fun EditFormCompact(
    accountInfoType: AccountDetail,
    headerText: String,
    updateHeaderText: (Char) -> Unit,
    newAccountDetail: String,
    confirmNewAccountDetail: String,
    errorPresent: Boolean,
    saveChanges: () -> Unit,
    updateNewAccountDetail: (String) -> Unit,
    updateConfirmNewAccountDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
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
            errorPresent = errorPresent,
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
    errorPresent: Boolean,
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
        )
    ) {
        Column(
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.large_padding))
        ) {
            TextField(
                value = newAccountDetail,
                onValueChange = { updateNewAccountDetail(it) },
                placeholder = { Text(text = stringResource(accountInfoType.stringId)) },
                isError = errorPresent,
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
                isError = errorPresent,
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
                supportingText = {
                    if (errorPresent) {
                        Text(text = stringResource(R.string.account_info_mismatch))
                    }
                },
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
            accountInfoType = AccountDetail.USERNAME,
            updateNewAccountDetail = { },
            headerText = "test",
            updateHeaderText = { },
            newAccountDetail = "",
            confirmNewAccountDetail = "",
            updateConfirmNewAccountDetail = { },
            errorPresent = false,
            saveChanges = { },
        )
    }
}