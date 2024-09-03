package com.example.antheia_plant_manager.util

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.antheia_plant_manager.R

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
fun OutlinedDisabledColorToActive(): TextFieldColors {
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
fun OutlinedTextFieldClickModifier(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): Modifier {
    return modifier
        .width(dimensionResource(id = R.dimen.textfield_size_mini))
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.text_field_shape_radius)))
        .clickable(
            interactionSource = interactionSource,
            indication = androidx.compose.material.ripple.rememberRipple(
                bounded = false,
                radius = dimensionResource(id = R.dimen.textfield_size_mini) /2
            ),
            onClickLabel = stringResource(R.string.select_watering_frequency)
        ) {
            onClick()
        }
}
