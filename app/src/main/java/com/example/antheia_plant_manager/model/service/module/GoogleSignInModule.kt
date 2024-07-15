package com.example.antheia_plant_manager.model.service.module

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.antheia_plant_manager.WEB_CLIENT_ID
import com.example.antheia_plant_manager.model.service.GoogleSignIn
import com.example.antheia_plant_manager.model.service.impl.GoogleSignInImplementation
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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