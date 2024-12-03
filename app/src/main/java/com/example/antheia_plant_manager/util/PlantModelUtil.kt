package com.example.antheia_plant_manager.util

import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.model.service.firestore.PlantModel

fun PlantModel.toPlant(): Plant {
    return Plant(
        plantId = 0,
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