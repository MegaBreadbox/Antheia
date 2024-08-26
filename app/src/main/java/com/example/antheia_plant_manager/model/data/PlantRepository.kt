package com.example.antheia_plant_manager.model.data

import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    suspend fun addPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

    suspend fun updatePlant(plant: Plant)

    fun getPlantLocations(): Flow<List<String>>

    fun getPlants(location: String): Flow<List<Plant>>
}