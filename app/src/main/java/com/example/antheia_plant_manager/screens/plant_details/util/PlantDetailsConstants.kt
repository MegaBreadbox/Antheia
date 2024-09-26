package com.example.antheia_plant_manager.screens.plant_details.util

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.antheia_plant_manager.R
import com.example.compose.extendedColorScheme

enum class TabList(val title: Int) {
    SUMMARY(R.string.summary),
    UPDATE(R.string.settings),
    NOTES(R.string.notes)
}

const val DISABLED_ALPHA = 0.38f

@Composable
fun ButtonDefaults.waterButtonColors(): ButtonColors {
    return this.buttonColors(
        containerColor = MaterialTheme.extendedColorScheme().water.colorContainer,
        contentColor = MaterialTheme.extendedColorScheme().water.onColorContainer,
        disabledContainerColor = MaterialTheme.extendedColorScheme().water.colorContainer.copy(DISABLED_ALPHA),
        disabledContentColor = MaterialTheme.extendedColorScheme().water.onColorContainer.copy(DISABLED_ALPHA)
    )
}
@Composable
fun ButtonDefaults.repotButtonColors(): ButtonColors {
    return this.buttonColors(
        containerColor = MaterialTheme.extendedColorScheme().soil.colorContainer,
        contentColor = MaterialTheme.extendedColorScheme().soil.onColorContainer,
        disabledContainerColor = MaterialTheme.extendedColorScheme().soil.colorContainer.copy(DISABLED_ALPHA),
        disabledContentColor = MaterialTheme.extendedColorScheme().soil.onColorContainer.copy(DISABLED_ALPHA)
    )
}
@Composable
fun ButtonDefaults.fertilizeButtonColors(): ButtonColors {
    return this.buttonColors(
        containerColor = MaterialTheme.extendedColorScheme().fertilizer.colorContainer,
        contentColor = MaterialTheme.extendedColorScheme().fertilizer.onColorContainer,
        disabledContainerColor = MaterialTheme.extendedColorScheme().fertilizer.colorContainer.copy(DISABLED_ALPHA),
        disabledContentColor = MaterialTheme.extendedColorScheme().fertilizer.onColorContainer.copy(DISABLED_ALPHA)
    )
}
