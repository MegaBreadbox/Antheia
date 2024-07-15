package com.example.antheia_plant_manager.screens.sign_in

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import com.example.antheia_plant_manager.util.measureStyle
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel<WelcomeViewModel>(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ){
        welcomeTextCompact(
            processWelcomeText = uiState.value.processWelcomeText,
            updateWelcomeText = { viewModel.updateWelcomeText(it) }
        )

        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))

        TextInputFormCompact(
            emailText = viewModel.emailText,
            emailValueChange = { viewModel.updateEmail(it) },
            passwordText = viewModel.passwordText,
            errorText = uiState.value.errorText?.asString(),
            passwordValueChange = { viewModel.updatePassword(it) },
            isPasswordVisible = uiState.value.isPasswordVisible,
            updateIsPasswordVisible = { viewModel.updateIsPasswordVisible() },
            signIn = {
                viewModel.signIn()
                keyboardController?.hide()
            },
            anonymousSignIn = { viewModel.anonymousSignIn() },
            googleSignIn = {
                coroutineScope.launch {
                    val getResponse = CredentialManager.create(context).getCredential(
                    context = context,
                    request = viewModel.getGoogleSignInRequest()
                    )
                    try {
                        if (getResponse.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            viewModel.googleSignIn(
                                GoogleIdTokenCredential.createFrom(getResponse.credential.data).idToken
                            )
                        }
                    } catch (e: Exception) {
                        Log.d(
                            "Google login exception",
                            "$e"
                        )
                    }
                }
            },
        )
    }
}

@Composable fun welcomeTextCompact(
    processWelcomeText: String,
    updateWelcomeText: (Char) -> Unit,
    welcomeText: String = stringResource(R.string.welcome_to_antheia),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = processWelcomeText) {
        if(welcomeText.length != processWelcomeText.length) {
            delay(100L)
            updateWelcomeText(welcomeText[processWelcomeText.length])
        }
    }
    Text(
        text = processWelcomeText,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
            .padding(top = dimensionResource(id = R.dimen.large_padding))
            .height(
                welcomeText.measureStyle(style = MaterialTheme.typography.displayLarge)
            )
    )

}

@Composable fun TextInputFormCompact(
    emailText: String,
    passwordText: String,
    isPasswordVisible: Boolean,
    errorText: String?,
    emailValueChange: (String) -> Unit,
    passwordValueChange: (String) -> Unit,
    updateIsPasswordVisible: () -> Unit,
    signIn: () -> Unit,
    anonymousSignIn: () -> Unit,
    googleSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
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
                    onValueChange =  { email -> emailValueChange(email) } ,
                    placeholder = { Text(text = stringResource(R.string.email))},
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent

                    ),
                    isError = errorText != null,
                    keyboardOptions = KeyboardOptions(
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
                    placeholder = { Text(text = stringResource(R.string.password))},
                    trailingIcon = {
                        if(!isPasswordVisible) {
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
                    visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    isError = errorText != null,
                    supportingText = { Text(text = errorText ?: "") },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            signIn()
                        }
                    ),
                    modifier = modifier
                        .width(dimensionResource(id = R.dimen.textfield_size_compact))
                )
                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))

                Button(
                    onClick = signIn,
                    modifier = modifier.width(dimensionResource(id = R.dimen.textfield_size_compact))
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                    )
                }
                Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.big_padding)))
                Image(
                    painter = painterResource(id = R.drawable.android_neutral_rd_si),
                    contentDescription = stringResource(R.string.sign_in_with_google),
                    modifier = modifier
                        .clickable {
                            googleSignIn()
                        }
                )
            }
        }
        Row() {
            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text("Create an account")
            }

            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.small_padding)))

            TextButton(
                onClick = anonymousSignIn
            ) {
                Text("Continue without an account")
            }
        }
    }
}
