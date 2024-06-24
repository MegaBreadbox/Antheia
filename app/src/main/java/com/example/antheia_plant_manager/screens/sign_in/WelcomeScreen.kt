package com.example.antheia_plant_manager.screens.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.antheia_plant_manager.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel<WelcomeViewModel>()
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
    ){
        val welcome = stringResource(R.string.welcome_to_antheia)
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = uiState.value.processWelcomeText) {
            if(welcome.length != uiState.value.processWelcomeText.length) {
                delay(100L)
                viewModel.updateWelcomeText(welcome[uiState.value.processWelcomeText.length])
            }
        }
        Text(
            text = uiState.value.processWelcomeText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier.padding(top = dimensionResource(id = R.dimen.large_padding))
        )
        TextInputForm()
    }
}

@Composable fun TextInputForm(
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {

        TextField(
            value = TextFieldValue( ""),
            onValueChange = { },
            placeholder = { Text(text = stringResource(R.string.email))},
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            modifier = modifier
        )

        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.large_padding)))

        TextField(
            value = TextFieldValue( ""),
            onValueChange = { },
            placeholder = { Text(text = stringResource(R.string.password))},
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.visibility_24dp_fill0_wght400_grad0_opsz24),
                    contentDescription = null,
                    modifier = modifier
                        .padding(end = dimensionResource(id = R.dimen.medium_padding))
                )
            },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            modifier = modifier
        )


    }
}
