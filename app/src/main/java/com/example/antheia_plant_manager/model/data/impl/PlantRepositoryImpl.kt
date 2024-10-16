package com.example.antheia_plant_manager.model.data.impl

import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.model.data.PlantDao
import com.example.antheia_plant_manager.model.data.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor (private val plantDao: PlantDao): PlantRepository {
    override suspend fun addPlant(plant: Plant) {
        plantDao.addPlant(plant)
    }

    override suspend fun deletePlant(plant: Plant) {
        plantDao.deletePlant(plant)
    }

    override suspend fun updatePlant(plant: Plant) {
        plantDao.updatePlant(plant)
    }

    override fun getPlantLocations(userId: String): Flow<List<String>> {
        return plantDao.getPlantLocations(userId)
    }

    override fun getPlants(userId: String, location: String): Flow<List<Plant>> {
        return plantDao.getPlants(userId, location)
    }

    override fun getPlantLocationSuggestions(query: String): Flow<List<String>> {
        return plantDao.getPlantLocationSuggestions(query)
    }

    override fun getPlant(userId: String, plantId: Int): Flow<Plant> {
        return plantDao.getPlant(userId, plantId)
    }

    override suspend fun getAllPlants(userId: String): List<Plant> {
        return plantDao.getAllPlants(userId)
    }

    override fun getAllPlantsFlow(userId: String): Flow<List<Plant>> {
        return plantDao.getAllPlantsFlow(userId)
    }

}