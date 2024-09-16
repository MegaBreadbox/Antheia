package com.example.antheia_plant_manager.screens.add_plant.util

import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.util.ComposeText


fun PlantEntry.toPlant(): Plant {
    return Plant(
        name = this.name,
        location = this.location,
        waterReminder = this.waterReminder,
        repottingReminder = this.repottingReminder,
        fertilizerReminder = this.fertilizerReminder,
        dateAdded = this.dateAdded,
        plantUserId = this.plantUserId,
    )
}

data class PlantEntry(
    val plantId: Int = 0,
    val plantUserId: String = "",
    val name: String = "",
    val location: String = "",
    val waterReminder: String = "", //formated, frequency+date
    val repottingReminder: String = "",
    val fertilizerReminder: String = "",
    val dateAdded: String = "",
)

data class UiState(
    val isWaterFrequencyDialogActive: Boolean = false,
    val isRepottingFrequencyDialogActive: Boolean = false,
    val isFertilizerFrequencyDialogActive: Boolean = false,
    val selectedReminder: String = "",
    val dateMap: Map<ComposeText, String> = mapOf(),
    val isAdvancedSettingsEnabled: Boolean = false,
)

enum class ReminderFrequency{
    WATERREMINDER, REPOTTINGREMINDER, FERTILIZERREMINDER
}
