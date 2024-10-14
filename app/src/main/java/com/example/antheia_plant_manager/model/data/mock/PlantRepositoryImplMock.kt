package com.example.antheia_plant_manager.model.data.mock

import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.model.data.PlantRepository
import com.example.antheia_plant_manager.screens.add_plant.util.PlantEntry
import com.example.antheia_plant_manager.screens.add_plant.util.toPlant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlantRepositoryImplMock: PlantRepository {
    override suspend fun addPlant(plant: Plant) {
    }

    override suspend fun deletePlant(plant: Plant) {
    }

    override suspend fun updatePlant(plant: Plant) {
    }

    override fun getPlantLocations(userId: String): Flow<List<String>> {
        return flowOf(listOf("Location 1", "Location 2"))
    }

    override fun getPlants(userId: String, location: String): Flow<List<Plant>> {
        return flowOf(
            listOf(
                PlantEntry().toPlant(),
                PlantEntry().toPlant()
            )
        )
    }

    override fun getPlantLocationSuggestions(query: String): Flow<List<String>> {
        return flowOf(listOf("Location 1", "Location 2"))
    }

    override fun getPlant(userId: String, plantId: Int): Flow<Plant> {
        return flowOf(PlantEntry().toPlant())
    }

    override suspend fun getAllPlants(userId: String): List<Plant> {
        return listOf(
                PlantEntry().toPlant(),
                PlantEntry().toPlant()
            )
    }
}