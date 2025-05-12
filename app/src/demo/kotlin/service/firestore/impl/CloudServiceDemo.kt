package service.firestore.impl

import com.mega_breadbox.antheia_plant_manager.model.service.firestore.CloudService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.PlantModel
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class CloudServiceImpl @Inject constructor(): CloudService {

    override fun userFlow(): Flow<UserModel> {
        return emptyFlow()
    }

    override suspend fun generatePlantId(): String {
        return ""
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
        return UserModel()
    }

    override suspend fun getAllUserData(): List<PlantModel> {
        return emptyList()
    }
}