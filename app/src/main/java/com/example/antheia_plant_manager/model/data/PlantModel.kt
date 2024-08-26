package com.example.antheia_plant_manager.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val plantId: Int,
    val name: String,
    val location: String,
    val waterReminder: String, //formatted reminderFormat:LastWaterDateLocal
    val repottingReminder: String,
    val fertilizerReminder: String,
)