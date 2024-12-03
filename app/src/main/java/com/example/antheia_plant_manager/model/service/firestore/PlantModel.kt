package com.example.antheia_plant_manager.model.service.firestore

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp

data class PlantModel(
    val plantUserId: String,
    val plantName: String,
    val location: String,
    val waterReminder: String, //format, frequency+date
    val repottingReminder: String,
    val fertilizerReminder: String,
    val dateAdded: String,
    val notes: String,
    @ServerTimestamp
    val timeStamp: FieldValue = FieldValue.serverTimestamp()

)
