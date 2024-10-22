package com.example.antheia_plant_manager.screens.add_plant.util

import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.util.ComposeText



data class UiState(
    val isWaterFrequencyDialogActive: Boolean = false,
    val isRepottingFrequencyDialogActive: Boolean = false,
    val isFertilizerFrequencyDialogActive: Boolean = false,
    val selectedReminder: String = "",
    val dateMap: Map<ComposeText, String> = mapOf(),
    val isAdvancedSettingsEnabled: Boolean = false,
)

