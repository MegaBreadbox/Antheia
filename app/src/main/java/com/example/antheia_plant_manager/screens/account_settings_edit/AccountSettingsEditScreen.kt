package com.example.antheia_plant_manager.screens.account_settings_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.screens.account_settings.util.AccountInfoType
import com.example.antheia_plant_manager.screens.sign_in.WelcomeTextCompact
import com.example.compose.AntheiaplantmanagerTheme

@Composable
fun AccountSettingsEditScreen(

) {
}

@Composable
fun EditFormCompact(
    accountInfoType: AccountInfoType,
    headerText: String,
    updateHeaderText: (Char) -> Unit,
    updateNewAccountDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .imePadding()
            .safeContentPadding()
    ) {
        WelcomeTextCompact(
            processWelcomeText = headerText,
            updateWelcomeText = { updateHeaderText(it) },
            welcomeText = stringResource(
                R.string.change_account_detail_header,
                accountInfoType.type.asString().lowercase()
            )
        )
        FormType(
            accountInfoType = accountInfoType,
            newAccountDetail = "",
            confirmNewAccountDetail = "",
            updateNewAccountDetail = { },
            updateConfirmNewAccountDetail = { }
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
        Button(
            onClick = { },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.big_padding))
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
fun FormType(
    accountInfoType: AccountInfoType,
    newAccountDetail: String,
    confirmNewAccountDetail: String,
    updateNewAccountDetail: (String) -> Unit,
    updateConfirmNewAccountDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
    ) {
        TextField(
            value = newAccountDetail,
            onValueChange = { updateNewAccountDetail(it) },
            placeholder = { Text(text = accountInfoType.type.asString()) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = if(accountInfoType is AccountInfoType.Email) KeyboardType.Email else KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = modifier
                .width(dimensionResource(id = R.dimen.textfield_size_compact))
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.huge_padding)))
        TextField(
            value = confirmNewAccountDetail,
            onValueChange = { updateConfirmNewAccountDetail(it) },
            placeholder = { Text(text = stringResource(
                R.string.confirm_text_form,
                accountInfoType.type.asString().lowercase()
            )) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = if(accountInfoType is AccountInfoType.Email) KeyboardType.Email else KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = modifier
                .width(dimensionResource(id = R.dimen.textfield_size_compact))
        )
    }
}

@Composable
@Preview
fun EditFormCompactPreview() {
    AntheiaplantmanagerTheme() {
        EditFormCompact(
            accountInfoType = AccountInfoType.Username("test"),
            updateNewAccountDetail = { },
            headerText = "test",
            updateHeaderText = { },
        )
    }
}