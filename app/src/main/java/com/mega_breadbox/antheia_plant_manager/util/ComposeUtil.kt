package com.mega_breadbox.antheia_plant_manager.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mega_breadbox.antheia_plant_manager.R
import kotlinx.coroutines.delay

@Composable
fun String.measureStyle(
    text: String = this,
    style: TextStyle = TextStyle.Default,
    screenWidth: Int = LocalContext.current.resources.displayMetrics.widthPixels
): Dp {
    val textMeasurement = Paragraph(
        text = text,
        style = style,
        constraints = Constraints(maxWidth = screenWidth),
        density = LocalDensity.current,
        fontFamilyResolver = LocalFontFamilyResolver.current
    )
    return (textMeasurement.lineCount * style.lineHeight.value).toInt().dp
}

@Composable
fun cardColor(index: Int): CardColors {
    return if(index % 2 == 0 ) {
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    } else {
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    }
}

@Composable
fun outlinedDisabledColorToActive(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        disabledPrefixColor = OutlinedTextFieldDefaults.colors().unfocusedPrefixColor,
        disabledLabelColor = OutlinedTextFieldDefaults.colors().unfocusedLabelColor,
        disabledContainerColor = OutlinedTextFieldDefaults.colors().unfocusedContainerColor,
        disabledTextColor = OutlinedTextFieldDefaults.colors().unfocusedTextColor,
        disabledTrailingIconColor = OutlinedTextFieldDefaults.colors().unfocusedTrailingIconColor,
        disabledLeadingIconColor = OutlinedTextFieldDefaults.colors().unfocusedLeadingIconColor,
        disabledPlaceholderColor =  OutlinedTextFieldDefaults.colors().unfocusedPlaceholderColor,
        disabledSuffixColor =  OutlinedTextFieldDefaults.colors().unfocusedSuffixColor,
        disabledSupportingTextColor =  OutlinedTextFieldDefaults.colors().unfocusedSupportingTextColor,
        disabledBorderColor = OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor

    )
}

@Composable
fun outlinedTextFieldClickModifier(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): Modifier {
    return modifier
        .width(dimensionResource(id = R.dimen.textfield_size_mini))
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)))
        .clickable(
            interactionSource = interactionSource,
            indication = ripple(),
            onClickLabel = stringResource(R.string.select_watering_frequency)
        ) {
            onClick()
        }
}

@Composable
fun Header(
    screenTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        Text(
            text = screenTitle,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

/**
 * Writes out a string defined by welcomeText using hoisted state with
 * processWelcomeText and updateWelcomeText
 */
@Composable fun WelcomeTextCompact(
    processWelcomeText: String,
    updateWelcomeText: (Char) -> Unit,
    modifier: Modifier = Modifier,
    welcomeText: String,
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
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
            .padding(top = dimensionResource(id = R.dimen.large_padding))
            .height(
                welcomeText.measureStyle(style = MaterialTheme.typography.displayLarge)
            )
    )

}
