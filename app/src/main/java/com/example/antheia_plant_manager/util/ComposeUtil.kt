package com.example.antheia_plant_manager.util

import android.content.Context
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
