package com.example.antheia_plant_manager.model.service.firestore

import kotlinx.coroutines.flow.Flow


interface CloudService {
    val userFlow: Flow<UserModel?>

    suspend fun addPlant(plant: PlantModel)

    suspend fun deletePlant(plant: PlantModel)

    suspend fun updatePlant(plant: PlantModel)

    suspend fun addUser()

    suspend fun updateUser(user: UserModel)

    suspend fun getUser(): UserModel?

    suspend fun getAllUserData(): List<PlantModel>
}