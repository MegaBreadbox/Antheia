package com.example.antheia_plant_manager.model.service.firestore.mock

import com.example.antheia_plant_manager.model.service.firestore.CloudService
import com.example.antheia_plant_manager.model.service.firestore.PlantModel
import com.example.antheia_plant_manager.model.service.firestore.UserModel
import kotlinx.coroutines.flow.Flow

class CloudServiceMock: CloudService {
    override val userFlow: Flow<UserModel?>
        get() = TODO("Not yet implemented")

    override suspend fun addPlant(plant: PlantModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlant(plant: PlantModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePlant(plant: PlantModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addUser() {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserModel? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUserData(): List<PlantModel> {
        TODO("Not yet implemented")
    }
}