package com.example.antheia_plant_manager.util

import com.example.antheia_plant_manager.model.data.Plant
import java.time.LocalDate

const val SUBSCRIBE_DELAY = 5000L

const val DATE_CHECK_DELAY = 60000L

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(
        this.split("+")[1]
    )
}

sealed class Reminder {
    data object BeforeReminder: Reminder()
    data object DuringReminder: Reminder()
    data object AfterReminder: Reminder()
    data object NotEnabled: Reminder()
}

fun determineReminder(reminderDate: String, currentDate: LocalDate): Reminder {
    return when {
        reminderDate.isEmpty() -> Reminder.NotEnabled
        reminderDate.toLocalDate().isBefore(currentDate) -> Reminder.AfterReminder
        reminderDate.toLocalDate().isEqual(currentDate) -> Reminder.DuringReminder
        reminderDate.toLocalDate().isAfter(currentDate) -> Reminder.BeforeReminder
        else -> Reminder.NotEnabled
    }
}

fun Plant.toPlantAlert(waterAlert: Reminder, repottingAlert: Reminder, fertilizerAlert: Reminder): PlantAlert {
    return PlantAlert(
        plantId = this.plantId,
        plantName = this.name,
        waterAlert = waterAlert,
        repottingAlert = repottingAlert,
        fertilizerAlert = fertilizerAlert
    )
}

data class PlantAlert(
    val plantId: Int = 0,
    val plantName: String = "",
    val waterAlert: Reminder = Reminder.NotEnabled,
    val repottingAlert: Reminder = Reminder.NotEnabled,
    val fertilizerAlert: Reminder = Reminder.NotEnabled
)
