package com.mega_breadbox.antheia_plant_manager.model.data.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mega_breadbox.antheia_plant_manager.model.data.Plant
import com.mega_breadbox.antheia_plant_manager.model.data.PlantDao

@Database(entities = [Plant::class], version = 7, exportSchema = false)
abstract class PlantDatabase: RoomDatabase() {

   abstract fun plantDao(): PlantDao
}