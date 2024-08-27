package com.example.antheia_plant_manager.model.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlant(plant: Plant)

    @Query("SELECT DISTINCT(location) from plant ORDER BY location ASC")
    fun getPlantLocations(): Flow<List<String>>

    @Query("SELECT * from plant WHERE location = :location ORDER BY name ASC")
    fun getPlants(location: String): Flow<List<Plant>>

}