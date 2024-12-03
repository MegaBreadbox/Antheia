package com.example.antheia_plant_manager.model.service.firestore

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp

data class UserModel(
    val username: String = "",
    val email: String = "",
    val uid: String = "",
    @ServerTimestamp
    val timeStamp: FieldValue = FieldValue.serverTimestamp()
)
