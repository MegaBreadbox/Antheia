package com.mega_breadbox.antheia_plant_manager.model.service.firestore

import com.mega_breadbox.antheia_plant_manager.model.data.Plant
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class PlantModel(
    val id: String = "",
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
) {
    constructor(): this("", "", "", "", "", "", "", "", "", Date())
}

fun PlantModel.toPlant(): Plant {
    return Plant(
        plantId = this.id,
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
