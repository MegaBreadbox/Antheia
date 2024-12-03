package com.example.antheia_plant_manager.model.service.firestore

import kotlinx.coroutines.flow.Flow


interface CloudService {

    suspend fun addPlant(plant: PlantModel)

    suspend fun deletePlant(plant: PlantModel)

    suspend fun updatePlant(plant: PlantModel)

    suspend fun addUser(user: UserModel)

    suspend fun updateUser(user: UserModel)

    suspend fun getUser(): UserModel?

    suspend fun getAllUserData(): List<PlantModel>
}