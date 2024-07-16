package com.example.antheia_plant_manager.welcome_screen.modules

import com.example.antheia_plant_manager.model.service.GoogleSignIn
import com.example.antheia_plant_manager.welcome_screen.mocks.GoogleSignInMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleSignInModule {

    @Provides
    @Singleton
    fun provideGoogleSigInMock(): GoogleSignIn {
        return
    }

}