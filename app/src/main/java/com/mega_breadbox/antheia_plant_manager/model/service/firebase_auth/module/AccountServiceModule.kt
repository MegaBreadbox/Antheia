package com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.module

import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.AccountService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import service.firebase_auth.impl.AccountServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountServiceModule {

    @Binds
    @Singleton
    abstract fun bindAccountService(
        accountServiceImpl: AccountServiceImpl
    ): AccountService
}