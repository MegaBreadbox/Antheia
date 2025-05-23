package com.mega_breadbox.antheia_plant_manager.model.data.impl

import com.mega_breadbox.antheia_plant_manager.model.data.Plant
import com.mega_breadbox.antheia_plant_manager.model.data.PlantDao
import com.mega_breadbox.antheia_plant_manager.model.data.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor (private val plantDao: PlantDao): PlantRepository {
    override suspend fun addPlant(plant: Plant): Long {
        return plantDao.addPlant(plant)
    }

    override suspend fun addPlants(plants: List<Plant>) {
        plantDao.addPlants(plants)
    }

    override suspend fun deletePlant(plant: Plant) {
        plantDao.deletePlant(plant)
    }

    override suspend fun updatePlant(plant: Plant) {
        plantDao.updatePlant(plant)
    }

    override suspend fun deleteUserData(userId: String) {
        plantDao.deleteUserData(userId)
    }

    override fun getPlantLocations(userId: String): Flow<List<String>> {
        return plantDao.getPlantLocations(userId)
    }

    override fun getPlants(userId: String, location: String): Flow<List<Plant>> {
        return plantDao.getPlants(userId, location)
    }

    override fun getPlantLocationSuggestions(userId: String, query: String): Flow<List<String>> {
        return plantDao.getPlantLocationSuggestions(userId, query)
    }

    override fun getPlant(userId: String, plantId: String): Flow<Plant> {
        return plantDao.getPlant(userId, plantId)
    }

    override suspend fun getPlantOneShot(userId: String, plantId: String): Plant {
        return plantDao.getPlantOneShot(userId, plantId)
    }

    override suspend fun getAllPlants(userId: String): List<Plant> {
        return plantDao.getAllPlants(userId)
    }

    override fun getAllPlantsFlow(userId: String): Flow<List<Plant>> {
        return plantDao.getAllPlantsFlow(userId)
    }

    override suspend fun getPlantNotes(userId: String, plantId: String): String {
        return plantDao.getPlantNotes(userId, plantId)
    }

}