package com.example.antheia_plant_manager.model.service.firestore

import com.example.antheia_plant_manager.model.data.Plant
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class PlantModel(
    val plantId: Int = 0,
    val plantUserId: String = "",
    val plantName: String = "",
    val location: String = "",
    val waterReminder: String = "", //format, frequency+date
    val repottingReminder: String = "",
    val fertilizerReminder: String = "",
    val dateAdded: String = "",
    val notes: String = "",
    @ServerTimestamp
    val timeStamp: Date = Date()
)

fun PlantModel.toPlant(): Plant {
    return Plant(
        plantId = this.plantId,
        plantUserId = this.plantUserId,
        name = this.plantName,
        location = this.location,
        waterReminder = this.waterReminder,
        repottingReminder = this.repottingReminder,
        fertilizerReminder = this.fertilizerReminder,
        dateAdded = this.dateAdded,
        notes = this.notes
    )
}
