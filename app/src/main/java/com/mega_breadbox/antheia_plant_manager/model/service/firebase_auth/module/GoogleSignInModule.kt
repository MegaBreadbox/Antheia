package com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.module

import com.mega_breadbox.antheia_plant_manager.model.service.firebase_auth.GoogleSignIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import service.firebase_auth.impl.GoogleSignInImplementation
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleSignInModule {

    @Provides
    @Singleton
    fun provideGoogleSignIn(): GoogleSignIn {
        val googleSignInImplementation = GoogleSignInImplementation()
        googleSignInImplementation.credentialRequest()
        return googleSignInImplementation
    }
}