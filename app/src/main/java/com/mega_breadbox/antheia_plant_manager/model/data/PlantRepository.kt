package com.mega_breadbox.antheia_plant_manager.model.data

import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    suspend fun addPlant(plant: Plant): Long

    suspend fun addPlants(plants: List<Plant>)

    suspend fun deletePlant(plant: Plant)

    suspend fun updatePlant(plant: Plant)

    suspend fun deleteUserData(userId: String)

    fun getPlantLocations(userId: String): Flow<List<String>>

    fun getPlants(userId: String, location: String): Flow<List<Plant>>

    fun getPlantLocationSuggestions(userId: String, query: String): Flow<List<String>>

    fun getPlant(userId: String, plantId: String): Flow<Plant>

    suspend fun getPlantOneShot(userId: String, plantId: String): Plant

    suspend fun getAllPlants(userId: String): List<Plant>

    fun getAllPlantsFlow(userId: String): Flow<List<Plant>>

    suspend fun getPlantNotes(userId: String, plantId: String): String
}