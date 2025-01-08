package com.mega_breadbox.antheia_plant_manager.model.service.firestore

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserModel(
    val username: String = "",
    val email: String = "",
    val uid: String = "",
    @ServerTimestamp
    val timeStamp: Date = Date()
) {
    constructor(): this("", "", "", Date())
}

