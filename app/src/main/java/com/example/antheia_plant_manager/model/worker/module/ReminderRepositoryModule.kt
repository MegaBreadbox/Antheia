package com.example.antheia_plant_manager.model.worker.module

import com.example.antheia_plant_manager.model.worker.ReminderRepository
import com.example.antheia_plant_manager.model.worker.impl.ReminderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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