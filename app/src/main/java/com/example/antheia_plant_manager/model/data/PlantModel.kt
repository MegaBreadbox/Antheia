package com.example.antheia_plant_manager.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val plantId: Int = 0,
    val plantUserId: String,
    val name: String,
    val location: String,
    val waterReminder: String,  //format, frequency+date
    val repottingReminder: String,
    val fertilizerReminder: String,
    val dateAdded: String,
    val notes: String
)