package com.mega_breadbox.antheia_plant_manager.screens.plant_details.util

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mega_breadbox.antheia_plant_manager.R
import com.mega_breadbox.compose.extendedColorScheme

enum class TabList(val title: Int) {
    SUMMARY(R.string.summary),
    UPDATE(R.string.settings),
    NOTES(R.string.notes)
}

enum class ButtonType() {
    WATER,
    REPOT,
    FERTILIZE,
}

@Composable
fun getTaskButtonColor(buttonType: ButtonType): Color {
    return when (buttonType) {
        ButtonType.WATER -> MaterialTheme.extendedColorScheme().water.onColorContainer
        ButtonType.REPOT -> MaterialTheme.extendedColorScheme().soil.onColorContainer
        ButtonType.FERTILIZE -> MaterialTheme.extendedColorScheme().fertilizer.onColorContainer
    }
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
