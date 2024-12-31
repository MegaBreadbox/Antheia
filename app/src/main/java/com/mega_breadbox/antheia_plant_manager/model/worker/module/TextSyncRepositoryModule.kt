package com.mega_breadbox.antheia_plant_manager.model.worker.module

import com.mega_breadbox.antheia_plant_manager.model.worker.TextSyncRepository
import com.mega_breadbox.antheia_plant_manager.model.worker.impl.TextSyncRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TextSyncRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTextSyncRepository(
        textSyncRepositoryImpl: TextSyncRepositoryImpl
    ): TextSyncRepository
}