package com.example.antheia_plant_manager.util

import com.example.antheia_plant_manager.model.data.Plant
import java.time.LocalDate
import kotlin.math.absoluteValue

const val SUBSCRIBE_DELAY = 5000L

const val DATE_CHECK_DELAY = 60000L

/**parses a string to a LocalDate object
 * the string should be in the format of "frequency+yyyy-MM-dd"
 */
fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(
        this.substringAfter("+"),
    )
}

fun String.daysSinceLastReminder(): Int {
    return this.toLocalDate().let {
        LocalDate.now().until(it).days.absoluteValue
    }
}

fun String.getReminderFrequency(): String {
    return this.substringBefore("+")
}

fun String.getLastReminderDate(): String {
    return this.substringAfter("+")
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

/**
 * Given a plant object this function returns true if the
 * reminder date is past due or the current day
 */
fun Plant.requiresAttention(): Boolean {
    val plantAlert = this.toPlantAlert(
        waterAlert = determineReminder(this.waterReminder, LocalDate.now()),
        repottingAlert = determineReminder(this.repottingReminder, LocalDate.now()),
        fertilizerAlert = determineReminder(this.fertilizerReminder, LocalDate.now())
    )
    return when {
        plantAlert.waterAlert == Reminder.DuringReminder ||
                plantAlert.waterAlert == Reminder.AfterReminder -> {
                    true
        }
        plantAlert.repottingAlert == Reminder.DuringReminder ||
                plantAlert.repottingAlert == Reminder.AfterReminder -> {
                    true
        }
        plantAlert.fertilizerAlert == Reminder.DuringReminder ||
                plantAlert.fertilizerAlert == Reminder.AfterReminder -> {
                    true
        }
        else -> false
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

fun Plant.toPlantAlert(): PlantAlert {
    return PlantAlert(
        plantId = this.plantId,
        plantName = this.name,
        waterAlert = determineReminder(this.waterReminder, LocalDate.now()),
        repottingAlert = determineReminder(this.repottingReminder, LocalDate.now()),
        fertilizerAlert = determineReminder(this.fertilizerReminder, LocalDate.now())
    )

}

data class PlantAlert(
    val plantId: Int = 0,
    val plantName: String = "",
    val waterAlert: Reminder = Reminder.NotEnabled,
    val repottingAlert: Reminder = Reminder.NotEnabled,
    val fertilizerAlert: Reminder = Reminder.NotEnabled
)
