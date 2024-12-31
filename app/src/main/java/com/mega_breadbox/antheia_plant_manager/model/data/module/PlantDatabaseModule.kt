package com.mega_breadbox.antheia_plant_manager.model.data.module

import android.content.Context
import androidx.room.Room
import com.mega_breadbox.antheia_plant_manager.model.data.PlantDao
import com.mega_breadbox.antheia_plant_manager.model.data.impl.PlantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantDatabaseModule {

    @Singleton
    @Provides
    fun providePlantDatabase(
        @ApplicationContext context: Context
    ): PlantDatabase {
        return Room
            .databaseBuilder(context, PlantDatabase::class.java, "plant_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePlantDao(plantDatabase: PlantDatabase): PlantDao {
        return plantDatabase.plantDao()
    }
}