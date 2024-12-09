package com.example.antheia_plant_manager.model.service.firestore.mock

import com.example.antheia_plant_manager.model.service.firestore.CloudService
import com.example.antheia_plant_manager.model.service.firestore.PlantModel
import com.example.antheia_plant_manager.model.service.firestore.UserModel
import kotlinx.coroutines.flow.Flow

class CloudServiceMock: CloudService {

    override fun userFlow(): Flow<UserModel> {
        TODO("Not yet implemented")
    }

    override suspend fun addPlant(plant: PlantModel) {
    }

    override suspend fun deletePlant(plant: PlantModel) {
    }

    override suspend fun updatePlant(plant: PlantModel) {
    }

    override suspend fun addUser() {
    }

    override suspend fun updateUser(user: UserModel) {
    }

    override suspend fun getUser(): UserModel? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUserData(): List<PlantModel> {
        TODO("Not yet implemented")
    }
}