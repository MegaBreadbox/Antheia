package com.mega_breadbox.antheia_plant_manager.model.data.module

import com.mega_breadbox.antheia_plant_manager.model.data.PlantRepository
import com.mega_breadbox.antheia_plant_manager.model.data.impl.PlantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlantRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlantRepository(
        plantRepositoryImpl: PlantRepositoryImpl
    ): PlantRepository
}