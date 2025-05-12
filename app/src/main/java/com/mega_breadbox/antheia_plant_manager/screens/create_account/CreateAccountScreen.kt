package com.mega_breadbox.antheia_plant_manager.screens.create_account

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.antheia_plant_manager.util.WelcomeTextCompact
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen(
    navigateHome: () -> Unit,
    isLinkAccount: Boolean = false,
    modifier: Modifier = Modifier,
    viewModel: CreateAccountViewModel = hiltViewModel<CreateAccountViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val privacyPolicyIntent = Intent(Intent.ACTION_VIEW,
        Uri.parse("https://raw.githubusercontent.com/MegaBreadbox/Antheia/refs/heads/master/PRIVACY_POLICY")
    )
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        WelcomeTextCompact(
            processWelcomeText = uiState.welcomeText,
            updateWelcomeText = { viewModel.changeWelcomeText(it) },
            welcomeText = stringResource(R.string.create_account)
        )

        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))

        CreateAccountForm(
            emailText = viewModel.emailText,
            passwordText = viewModel.passwordText,
            confirmPasswordText = viewModel.confirmPasswordText,
            isPasswordVisible = uiState.isPasswordVisible,
            errorText = uiState.errorText?.asString(),
            onPrivacyPolicyClick = { context.startActivity(privacyPolicyIntent) },
            isLinkAccount = isLinkAccount,
            emailValueChange = { viewModel.updateEmailText(it) },
            passwordValueChange = { viewModel.updatePasswordText(it) },
            confirmPasswordValueChange = { viewModel.updateConfirmPasswordText(it) },
            updateIsPasswordVisible = { viewModel.updateIsPasswordVisible() },
            createAccount = {
                viewModel.createAccount(navigateHome)
            },
            googleSignIn = {
                coroutineScope.launch {
                    try {
                        val getResponse = CredentialManager.create(context).getCredential(
                            context = context,
                            request = viewModel.getGoogleSignInRequest()
                        )
                        viewModel.googleSignIn(getResponse, navigateHome)
                    } catch (e: GetCredentialCancellationException) {
                        viewModel.updateErrorText(R.string.google_sign_in_cancelled)
                    }
                }
            },
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))
    }
}

@Composable
fun CreateAccountForm(
    emailText: String,
    passwordText: String,
    confirmPasswordText: String,
    isPasswordVisible: Boolean,
    errorText: String?,
    onPrivacyPolicyClick: () -> Unit,
    emailValueChange: (String) -> Unit,
    passwordValueChange: (String) -> Unit,
    confirmPasswordValueChange: (String) -> Unit,
    updateIsPasswordVisible: () -> Unit,
    createAccount: () -> Unit,
    googleSignIn: () -> Unit,
    isLinkAccount: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.medium_padding))
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(dimensionResource(id = R.dimen.large_padding))
            ) {
                TextField(
                    value = emailText,
                    onValueChange = { email -> emailValueChange(email) },
                    placeholder = { Text(text = stringResource(R.string.email)) },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent

                    ),
                    isError = errorText != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = modifier
                        .width(dimensionResource(id = R.dimen.textfield_size_compact))

                )

                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))

                TextField(
                    value = passwordText,
                    onValueChange = { passwordValueChange(it) },
                    placeholder = { Text(text = stringResource(R.string.password)) },
                    trailingIcon = {
                        if (!isPasswordVisible) {
                            IconButton(
                                onClick = { updateIsPasswordVisible() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_24dp_fill0_wght400_grad0_opsz24),
                                    contentDescription = null,
                                    modifier = modifier
                                        .padding(end = dimensionResource(id = R.dimen.medium_padding))
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { updateIsPasswordVisible() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_off_24dp_fill0_wght400_grad0_opsz24),
                                    contentDescription = null,
                                    modifier = modifier
                                        .padding(end = dimensionResource(id = R.dimen.medium_padding))
                                )
                            }

                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    isError = errorText != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    modifier = modifier
                        .width(dimensionResource(id = R.dimen.textfield_size_compact))
                )

                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))

                TextField(
                    value = confirmPasswordText,
                    onValueChange = { confirmPasswordValueChange(it) },
                    placeholder = { Text(text = stringResource(R.string.confirm_password)) },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    isError = errorText != null,
                    supportingText = { Text(text = errorText ?: "") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            createAccount()
                        }
                    ),
                    modifier = modifier
                        .width(dimensionResource(id = R.dimen.textfield_size_compact))
                )

                TextButton(
                    onClick = onPrivacyPolicyClick
                ) {
                    Text(stringResource(R.string.review_privacy_policy))
                }


                Button(
                    onClick = createAccount,
                    modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_compact))
                ) {
                    Text(
                        text = stringResource(R.string.create_account),
                    )
                }
                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.medium_padding)))
                if(isLinkAccount) {
                    Image(
                        painter = painterResource(id = R.drawable.android_neutral_rd_si),
                        contentDescription = stringResource(R.string.sign_in_with_google),
                        modifier = modifier
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.google_sign_in_button_shape)))
                            .clickable {
                                googleSignIn()
                            }
                    )

                }

            }
        }
    }
}

