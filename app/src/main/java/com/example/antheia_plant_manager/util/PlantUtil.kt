package com.example.antheia_plant_manager.util

import com.example.antheia_plant_manager.model.data.Plant
import java.time.LocalDate
import kotlin.math.absoluteValue

const val SUBSCRIBE_DELAY = 5000L

const val DATE_CHECK_DELAY = 60000L

enum class CalendarFrequency {
    DAYS,
    WEEKS,
    MONTHS,
    YEARS
}

/**parses a string to a LocalDate object
 * the string should be in the format of "frequency+yyyy-MM-dd"
 */
fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(
        this.substringAfter("+"),
    )
}

/**
 * takes a reminder string formatted "frequency+yyyy-MM-dd" and adds
 * time to it based on the frequency listed on the string
 */
fun String.updateReminderDate(): String {
    return when(this.getReminderFrequency()) {
        "" -> ""
        "Every day" -> this.addToReminder(1, CalendarFrequency.DAYS)
        "Every 2 days" -> this.addToReminder(2, CalendarFrequency.DAYS)
        "Every 3 days" -> this.addToReminder(3, CalendarFrequency.DAYS)
        "Every week" -> this.addToReminder(1, CalendarFrequency.WEEKS)
        "Every 2 weeks" -> this.addToReminder(2, CalendarFrequency.WEEKS)
        "Every 3 weeks" -> this.addToReminder(3, CalendarFrequency.WEEKS)
        "Every month" -> this.addToReminder(1, CalendarFrequency.MONTHS)
        "Every 6 months" -> this.addToReminder(6, CalendarFrequency.MONTHS)
        "Every year" -> this.addToReminder(1, CalendarFrequency.YEARS)
        "Every 2 years" -> this.addToReminder(2, CalendarFrequency.YEARS)
        "Every 4 years" -> this.addToReminder(4, CalendarFrequency.YEARS)
        else -> ""
   }
}

fun String.daysSinceLastReminder(): Int {
    return this.toLocalDate().let {
        LocalDate.now().until(it).days.absoluteValue
    }
}

private fun String.addToReminder(frequency: Int, frequencyType: CalendarFrequency): String {

    val newReminder = when(frequencyType) {
        CalendarFrequency.DAYS -> LocalDate.now().plusDays(frequency.toLong()).toString()
        CalendarFrequency.WEEKS -> LocalDate.now().plusWeeks(frequency.toLong()).toString()
        CalendarFrequency.MONTHS -> LocalDate.now().plusMonths(frequency.toLong()).toString()
        CalendarFrequency.YEARS -> LocalDate.now().plusYears(frequency.toLong()).toString()
    }
    return this.getReminderFrequency().plus("+$newReminder")
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

fun PlantEntry.determineReminderText(reminder: ReminderFrequency): String {
    return when(reminder) {
        ReminderFrequency.WATERREMINDER -> this.waterReminder
        ReminderFrequency.REPOTTINGREMINDER -> this.repottingReminder
        ReminderFrequency.FERTILIZERREMINDER -> this.fertilizerReminder
    }
}

fun PlantEntry.toPlant(): Plant {
    return Plant(
        plantId = if(this.plantId == 0) 0 else this.plantId,
        name = this.name,
        location = this.location,
        waterReminder = this.waterReminder,
        repottingReminder = this.repottingReminder,
        fertilizerReminder = this.fertilizerReminder,
        dateAdded = this.dateAdded,
        plantUserId = this.plantUserId,
    )
}

fun Plant.toPlantEntry(): PlantEntry {
    return PlantEntry(
        plantId = this.plantId,
        plantUserId = this.plantUserId,
        name = this.name,
        location = this.location,
        waterReminder = this.waterReminder,
        repottingReminder = this.repottingReminder,
        fertilizerReminder = this.fertilizerReminder,
        dateAdded = this.dateAdded
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
