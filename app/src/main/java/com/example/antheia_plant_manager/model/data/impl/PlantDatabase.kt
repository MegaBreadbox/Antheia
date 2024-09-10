package com.example.antheia_plant_manager.model.data.impl

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.antheia_plant_manager.model.data.Plant
import com.example.antheia_plant_manager.model.data.PlantDao

@Database(entities = [Plant::class], version = 2, exportSchema = false)
abstract class PlantDatabase: RoomDatabase() {

   abstract fun plantDao(): PlantDao
}