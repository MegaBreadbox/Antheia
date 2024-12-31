package com.mega_breadbox.antheia_plant_manager.model.service.firestore.module

import com.mega_breadbox.antheia_plant_manager.model.service.firestore.CloudService
import com.mega_breadbox.antheia_plant_manager.model.service.firestore.impl.CloudServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CloudServiceModule {

    @Binds
    @Singleton
    abstract fun bindCloudService(cloudServiceImpl: CloudServiceImpl): CloudService
}