package com.example.antheia_plant_manager.model.data

import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    suspend fun addPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

    suspend fun updatePlant(plant: Plant)

    fun getPlantLocations(userId: String): Flow<List<String>>

    fun getPlants(userId: String, location: String): Flow<List<Plant>>

    fun getPlantLocationSuggestions(query: String): Flow<List<String>>

    fun getPlant(userId: String, plantId: Int): Flow<Plant>

    suspend fun getAllPlants(userId: String): List<Plant>
}