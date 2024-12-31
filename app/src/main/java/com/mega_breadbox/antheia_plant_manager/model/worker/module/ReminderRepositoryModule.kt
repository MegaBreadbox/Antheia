package com.mega_breadbox.antheia_plant_manager.model.worker.module

import com.mega_breadbox.antheia_plant_manager.model.worker.ReminderRepository
import com.mega_breadbox.antheia_plant_manager.model.worker.impl.ReminderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReminderRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindReminderRepository(
        reminderRepositoryImpl: ReminderRepositoryImpl
    ): ReminderRepository

}