package com.mega_breadbox.antheia_plant_manager.screens.add_plant.util

import com.mega_breadbox.antheia_plant_manager.util.ComposeText



data class UiState(
    val isWaterFrequencyDialogActive: Boolean = false,
    val isRepottingFrequencyDialogActive: Boolean = false,
    val isFertilizerFrequencyDialogActive: Boolean = false,
    val selectedReminder: String = "",
    val dateMap: Map<ComposeText, String> = mapOf(),
    val isAdvancedSettingsEnabled: Boolean = false,
)

