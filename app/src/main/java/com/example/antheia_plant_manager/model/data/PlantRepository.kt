package com.example.antheia_plant_manager.model.data

import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    suspend fun addPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

    suspend fun updatePlant(plant: Plant)

    suspend fun deleteUserData(userId: String)

    fun getPlantLocations(userId: String): Flow<List<String>>

    fun getPlants(userId: String, location: String): Flow<List<Plant>>

    fun getPlantLocationSuggestions(userId: String, query: String): Flow<List<String>>

    fun getPlant(userId: String, plantId: Int): Flow<Plant>

    suspend fun getPlantOneShot(userId: String, plantId: Int): Plant

    suspend fun getAllPlants(userId: String): List<Plant>

    fun getAllPlantsFlow(userId: String): Flow<List<Plant>>

    suspend fun getPlantNotes(userId: String, plantId: Int): String
}